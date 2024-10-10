package com.just.controllers.one;

import com.just.entities.one.Store;
import com.just.services.one.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class StoreController {

    private static final Logger logger = LoggerFactory.getLogger(Store.class);

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<Store> createStore(@RequestBody Store store) {
        Store savedStore = storeService.saveStore(store);
        return ResponseEntity.ok(savedStore);
    }
}
