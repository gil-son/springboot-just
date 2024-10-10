package com.just.repositories;

import com.just.entities.one.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Address, Long> {
}
