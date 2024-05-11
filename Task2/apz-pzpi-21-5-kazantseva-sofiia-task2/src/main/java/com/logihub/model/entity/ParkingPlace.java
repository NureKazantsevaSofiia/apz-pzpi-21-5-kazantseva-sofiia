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
@Table(name = "parking_places")
public class ParkingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_place_id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "place_number")
    private String placeNumber;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parking_manager_id")
    private ParkingManager parkingManager;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "parkingPlace")
    @JsonIgnore
    @Column(name = "invoices")
    private List<Invoice> invoices;

    @Column(name = "min_width")
    private Double minWidth;

    @Column(name = "min_height")
    private Double minHeight;

    @Column(name = "min_length")
    private Double minLength;

    @Column(name = "min_weight")
    private Double minWeight;

    @Column(name = "max_width")
    private Double maxWidth;

    @Column(name = "max_height")
    private Double maxHeight;

    @Column(name = "max_length")
    private Double maxLength;

    @Column(name = "max_weight")
    private Double maxWeight;

    @Column(name = "hourly_pay")
    private Double hourlyPay;
}