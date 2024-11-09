package org.esfinge.virtuallab.polyglot;

import java.util.ArrayList;
import java.util.List;

public class SecondaryInfo {

    private String url;
    private String user;
    private String password;
    private String dialect;
    private List<Class<?>> mappedClasses = new ArrayList<>();

    public SecondaryInfo() {
    }

    public SecondaryInfo(String url, String user, String password, String dialect) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.dialect = dialect;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public List<Class<?>> getMappedClasses() {
        return mappedClasses;
    }

    public void setMappedClasses(List<Class<?>> mappedClasses) {
        this.mappedClasses = mappedClasses;
    }

}
