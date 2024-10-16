package com.just.entities.multiples;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Table(name = "tb_lesson")
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String materia;
    private String hours;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Constructors, Getters, and Setters

    public Lesson(String materia, String hours) {
        this.materia = materia;
        this.hours = hours;
    }
}

