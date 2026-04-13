package com.example.license_system_gradle.controller;


import com.example.license_system_gradle.service.KeyValueService;

import com.example.license_system_gradle.store.LoadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class KeyValueController {

    @Autowired
    private KeyValueService keyValueService;

    @Autowired
    private LoadData loaddata;

    @GetMapping("/get/{key}")
    public ResponseEntity<?> getValue(@PathVariable String key){
        return  ResponseEntity.ok(keyValueService.getValue(key));
    }

    @GetMapping("/getKeys")
    public ResponseEntity<List<String>> getKeys() {
        return ResponseEntity.ok(keyValueService.getKeys());
    }

    @GetMapping("/getKeyValuePairs")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(keyValueService.getAll());
    }

    @PostMapping("/refresh")
    public String refresh() {
        loaddata.loadData(); // reload from DB
        return "Cache refreshed!";
    }
}
