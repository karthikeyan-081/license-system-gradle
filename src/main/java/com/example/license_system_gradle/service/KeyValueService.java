package com.example.license_system_gradle.service;

import com.example.license_system_gradle.repository.KeyValueRepo;
import com.example.license_system_gradle.store.KeyValuePairStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KeyValueService {

    @Autowired
    private KeyValuePairStore store;
    @Autowired
    private KeyValueRepo repo;

    public String getValue(String key) {
        if (store.containsKey(key)) {
            return store.get(key);
        }

//        Optional<KeyValuePair> kv = repo.findById(key);
//        if (kv.isPresent()) {
//          store.put(key, kv.get().getValue());
//            return kv.get().getValue();
//        }

        return "Key with value not found";
    }

    public List<String> getKeys(){
        return store.getKeys();
    }

    public Map<String,String> getAll(){
        return store.getAll();
    }
}
