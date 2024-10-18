package com.just.repositories.multiples;

import com.just.entities.multiples.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
