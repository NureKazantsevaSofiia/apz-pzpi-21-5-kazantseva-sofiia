package com.logihub.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "price")
    private Double price;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "total_weight")
    private Double totalWeight;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
