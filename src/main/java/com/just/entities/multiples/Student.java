package com.just.entities.multiples;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Table(name = "tb_student")
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonBackReference // Prevents recursion
    private School school;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference // Handles bidirectional serialization
    private List<Lesson> lessons;

    public Student(String name, String dateOfBirth, List<Lesson> lessons) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.lessons = lessons;
    }

}

