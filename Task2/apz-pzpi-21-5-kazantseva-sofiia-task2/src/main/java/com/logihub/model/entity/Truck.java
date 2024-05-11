package com.logihub.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trucks")
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "truck_id")
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "truck_manager_id")
    private TruckManager truckManager;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "truck")
    @JsonIgnore
    @Column(name = "invoices")
    private List<Invoice> invoices;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "length")
    private Double length;

    @Column(name = "weight")
    private Double weight;
}
