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
@Table(name = "truck_companies")
public class TruckCompany extends Company {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "company")
    @JsonIgnore
    @Column(name = "truck_managers")
    private List<TruckManager> truckManagers;

    public TruckCompany() {
        this.setType(CompanyType.TRUCK_COMPANY);
    }
}
