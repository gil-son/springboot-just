package com.just.controllers.multiples;

import com.just.entities.multiples.School;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.just.services.multiples.SchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/school")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    // Add ObjectMapper for JSON mapping
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(School.class);

    @PostMapping(value = "/mapperless")
    public ResponseEntity<School> createSchool(@RequestBody School school) {
        School savedSchool = schoolService.saveSchool(school);
        return new ResponseEntity<>(savedSchool, HttpStatus.CREATED);
    }

    @PostMapping(value = "/mapperless-event")
    public ResponseEntity<School> createSchoolEvent(@RequestBody School school) {
        School savedSchool = schoolService.saveSchoolEvent(school);
        return new ResponseEntity<>(savedSchool, HttpStatus.CREATED);
    }

    @PostMapping(value = "/mapper")
    public ResponseEntity<School> createSchoolMapper(@RequestBody Map<String, Object> schoolMap) {

        logger.info("SchoolData: {}", schoolMap);

        // Use ObjectMapper to map the schoolMap to School object
        School school = objectMapper.convertValue(schoolMap.get("school"), School.class);

        School savedSchool = schoolService.saveSchool(school);
        return new ResponseEntity<>(savedSchool, HttpStatus.CREATED);
    }
}
