package com.logihub.repository;

import com.logihub.model.entity.Company;
import com.logihub.model.entity.Truck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TruckRepository extends JpaRepository<Truck, Long> {

    Optional<Truck> findByNumber(String truckNumber);

    Page<Truck> findAllByTruckManager_Company(Company company, Pageable pageable);

    Page<Truck> findAllByTruckManager_CompanyAndTruckManagerIsNull(Company company, Pageable pageable);
}