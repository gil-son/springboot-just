package com.just.services.multiples;

import com.just.entities.multiples.Lesson;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddHoursServiceImp implements AddHoursInterface {

    @Override
    public Lesson AddHours(Lesson lesson) {
            lesson.setHours("6h");
        return lesson;
    }
}
