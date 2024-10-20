package com.just.services.multiples;

import com.just.entities.multiples.School;
import com.just.repositories.multiples.LessonRepository;
import com.just.repositories.multiples.SchoolRepository;
import com.just.repositories.multiples.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    public School saveSchool(School school) {
        // Iterate through students and lessons to ensure proper relationships
        school.getStudents().forEach(student -> {
            student.setSchool(school); // Set the relationship for School -> Students

            // Map each lesson to the student
            student.getLessons().forEach(lesson -> {
                lesson.setStudent(student); // Set the relationship for Student -> Lessons
                lessonRepository.save(lesson); // Save each lesson
            });

            studentRepository.save(student); // Save each student after lessons
        });

        // Save the school after mapping the relationships
        return schoolRepository.save(school);
    }
}
