package com.just.entities.text;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Table(name = "tb_product")
@Entity
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_value", precision = 10, scale = 2) // Renomeie aqui
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Product(int productId, BigDecimal value) {
        this.productId = productId;
        this.value = value;
    }
}
