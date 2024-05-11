package com.logihub.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logihub.model.enums.InvoiceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type")
    private InvoiceType type;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parking_place")
    private ParkingPlace parkingPlace;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "invoice")
    @JsonIgnore
    @Column(name = "items")
    private List<Item> items;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "truck_manager_id")
    private TruckManager truckManager;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parking_manager_id")
    private ParkingManager parkingManager;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "signed_by_truck_manager_date")
    private LocalDateTime signedByTruckManagerDate;

    @Column(name = "signed_by_parking_manager_date")
    private LocalDateTime signedByParkingManagerDate;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_signed_by_truck_manager")
    private Boolean signedByTruckManager;

    @Column(name = "is_signed_by_parking_manager")
    private Boolean signedByParkingManager;
}
