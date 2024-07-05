package org.esfinge.virtuallab.polyglot;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
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

}
