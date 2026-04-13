package com.example.license_system_gradle.store;

import com.example.license_system_gradle.Model.KeyValuePair;
import com.example.license_system_gradle.repository.KeyValueRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoadData {
    private final KeyValueRepo repo;
    private final KeyValuePairStore store;

    public LoadData(KeyValueRepo repo, KeyValuePairStore store) {
        this.repo = repo;
        this.store = store;
    }

    @Scheduled(fixedRate = 10000)
    public void refreshData() {
        log.info("Refreshing data every 1 min...");
        loadData();
    }
    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        log.info("Loading data from DB...");

        try {
            var list = repo.findAll();

            if (list.isEmpty()) {
                log.error("No data found in DB.");
                return;
            }

            for (KeyValuePair kv : list) {
                store.put(kv.getKey(), kv.getValue());
            }

            log.info("Data loaded successfully!");

        } catch (Exception e) {
            log.error("Error while loading data: " + e.getMessage());
        }
    }
}