package com.just.services.one;

import com.just.entities.one.Store;
import com.just.repositories.StoreRepository;

public class StoreService {

    private StoreRepository storeRepository;

    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }
}
