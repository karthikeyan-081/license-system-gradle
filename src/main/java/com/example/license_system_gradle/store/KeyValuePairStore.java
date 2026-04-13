package com.example.license_system_gradle.store;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KeyValuePairStore {
    private Map<String, String> store = new ConcurrentHashMap<>();

    public String get(String key) {
        return store.get(key);
    }

    protected void put(String key, String value) {
        store.put(key, value);
    }

    public boolean containsKey(String key) {
        return store.containsKey(key);
    }

    public List<String> getKeys(){
        return new ArrayList<>(store.keySet());
    }
    public Map<String, String> getAll() {
        return Collections.unmodifiableMap(store);
    }

    public void clear() {
        store.clear();
    }

}