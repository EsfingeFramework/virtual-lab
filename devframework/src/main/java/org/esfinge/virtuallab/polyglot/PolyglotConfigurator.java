package org.esfinge.virtuallab.polyglot;

import java.util.HashMap;
import java.util.Map;

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

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Map<String, SecondaryInfo> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, SecondaryInfo> configs) {
        this.configs = configs;
    }

}
