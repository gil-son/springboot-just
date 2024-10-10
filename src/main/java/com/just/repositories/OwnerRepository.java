package com.just.repositories;

import com.just.entities.one.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Store, Long> {
}
