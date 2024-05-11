package com.logihub.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logihub.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@Entity
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@Table(name = "parking_managers")
public class ParkingManager extends User {

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parking_company_id")
    private ParkingCompany company;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "parkingManager")
    @JsonIgnore
    @Column(name = "places")
    private List<ParkingPlace> places;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "parkingManager")
    @JsonIgnore
    @Column(name = "invoices")
    private List<Invoice> invoices;

    public ParkingManager() {
        this.setRole(Role.PARKING_MANAGER);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ParkingManager that = (ParkingManager) o;
        return Objects.equals(company, that.company) && Objects.equals(places, that.places)
                && Objects.equals(invoices, that.invoices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), company, places, invoices);
    }
}
