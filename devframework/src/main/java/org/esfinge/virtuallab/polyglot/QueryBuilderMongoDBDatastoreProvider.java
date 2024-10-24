package org.esfinge.virtuallab.polyglot;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import static esfinge.querybuilder.core.utils.PersistenceTypeConstants.MONGODB;
import esfinge.querybuilder.mongodb.DatastoreProvider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryBuilderMongoDBDatastoreProvider implements DatastoreProvider {

    protected MongoClient mongo;
    private final String host;
    private final int port;
    private final String database;
    private Datastore datastore;

    public QueryBuilderMongoDBDatastoreProvider() {
        var pc = PolyglotConfigurator.getInstance();
        var secInfo = pc.getConfigs().get(MONGODB);
        Thread.currentThread().setContextClassLoader(pc.getClassLoader());
        for (var clazz : secInfo.getMappedClasses()) {
            mappClass(clazz);
        }
        host = (String) getInfo("host", secInfo);
        port = Integer.parseInt(getInfo("port", secInfo));
        database = (String) getInfo("database", secInfo);
    }

    public MongoClient getMongo() {
        try {
            mongo = MongoClients.create(
                    MongoClientSettings.builder()
                            .applyToClusterSettings(builder
                                    -> builder.hosts(Collections.singletonList(new ServerAddress(host, port))))
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mongo;
    }

    @Override
    public Datastore getDatastore() {
        if (datastore == null) {
            datastore = Morphia.createDatastore(getMongo(), database);
        }
        return datastore;
    }

    @Override
    public void mappClass(Class<?> clazz) {
        getDatastore().getMapper().map(clazz);
        getDatastore().ensureIndexes();
    }

    private String getInfo(String key, SecondaryInfo secInfo) {
        String url = secInfo.getUrl();
        String regex = "^[^:]+://([^:/]+):(\\d+)/(\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            var secHost = matcher.group(1);
            var secPort = matcher.group(2);
            var secDatabase = matcher.group(3);
            Map<String, String> infoMap = new HashMap<>();
            infoMap.put("host", secHost);
            infoMap.put("port", secPort);
            infoMap.put("database", secDatabase);
            return infoMap.get(key);
        }
        return null;
    }
}
