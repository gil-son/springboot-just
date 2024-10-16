package com.just.entities.multiples;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Table(name = "tb_school")
@Entity
public class School implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Student> students;

    public School(List<Student> students) {
        this.students = students;
    }
}

