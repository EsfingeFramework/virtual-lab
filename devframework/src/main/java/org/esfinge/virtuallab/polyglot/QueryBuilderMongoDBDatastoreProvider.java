package org.esfinge.virtuallab.polyglot;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import static esfinge.querybuilder.core.utils.PersistenceTypeConstants.MONGODB;
import esfinge.querybuilder.mongodb.DatastoreProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class QueryBuilderMongoDBDatastoreProvider implements DatastoreProvider {

    private final Morphia morphia;
    protected MongoClient mongo;
    private final String host;
    private final int port;
    private final String database;

    public QueryBuilderMongoDBDatastoreProvider() {
        morphia = new Morphia();
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
        if (mongo == null) {
            try {
                mongo = new MongoClient(host, port);
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
        return mongo;
    }

    @Override
    public Datastore getDatastore() {
        return getMorphia().createDatastore(getMongo(), database);
    }

    @Override
    public Morphia getMorphia() {
        return morphia;
    }

    @Override
    public final void mappClass(Class<?> clazz) {
        getMorphia().map(clazz);
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
