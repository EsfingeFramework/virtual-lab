package org.esfinge.virtuallab.polyglot;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class PolyglotConfigurator {

    private static PolyglotConfigurator _instance;
    private ClassLoader classLoader;

    private Map<String, SecondaryInfo> configs = new HashMap();

    public static PolyglotConfigurator getInstance() {
        if (_instance == null) {
            _instance = new PolyglotConfigurator();
        }

        return _instance;
    }

}
