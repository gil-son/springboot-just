package com.just.controllers.one;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.just.entities.one.Store;
import com.just.services.one.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/store")
public class StoreController {

    private static final Logger logger = LoggerFactory.getLogger(Store.class);

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping(value = "/mapperless")
    public ResponseEntity<Store> createStoreMapperLess(@RequestBody Store store) {
        Store savedStore = storeService.saveStore(store);
        return ResponseEntity.ok(savedStore);
    }

    @PostMapping(value = "/mapper")
    public ResponseEntity<Store> createStoreUsingMapper(@RequestBody Map<String, Object> storeData) {

        logger.info("StoreData: {}", storeData);

        // Extract the nested store object from the JSON
        Map<String, Object> storeMap = (Map<String, Object>) storeData.get("store");

        // Manually map the JSON structure to a Store entity
        ObjectMapper objectMapper = new ObjectMapper();
        Store store = objectMapper.convertValue(storeMap, Store.class);

        logger.info("Store: {}", store);

        Store savedStore = storeService.saveStore(store);
        return ResponseEntity.ok(savedStore);
    }

}
