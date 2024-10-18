package com.just.repositories.multiples;

import com.just.entities.multiples.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
