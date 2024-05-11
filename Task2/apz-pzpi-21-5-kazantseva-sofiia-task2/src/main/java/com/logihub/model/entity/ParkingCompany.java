package com.logihub.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logihub.model.enums.CompanyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@Table(name = "parking_companies")
public class ParkingCompany extends Company {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "company")
    @JsonIgnore
    @Column(name = "parking_managers")
    private List<ParkingManager> parkingManagers;

    public ParkingCompany() {
        this.setType(CompanyType.PARKING_COMPANY);
    }
}
