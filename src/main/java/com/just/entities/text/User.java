package com.just.entities.text;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@ToString
@NoArgsConstructor
@Table(name = "tb_user")
@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "name", length = 45)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference // evita recursividade
    private List<Order> orders = new ArrayList<>();

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }
}
