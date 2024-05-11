package com.logihub.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logihub.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@Table(name = "truck_managers")
public class TruckManager extends User {

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "truck_company_id")
    private TruckCompany company;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "truckManager")
    @JsonIgnore
    @Column(name = "trucks")
    private List<Truck> trucks = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "truckManager")
    @JsonIgnore
    @Column(name = "invoices")
    private List<Invoice> invoices = new ArrayList<>();

    public TruckManager() {
        this.setRole(Role.TRUCK_MANAGER);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TruckManager that = (TruckManager) o;
        return Objects.equals(company, that.company) && Objects.equals(trucks, that.trucks)
                && Objects.equals(invoices, that.invoices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), company, trucks, invoices);
    }
}
