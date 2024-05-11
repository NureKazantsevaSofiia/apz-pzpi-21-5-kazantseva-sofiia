package com.logihub.repository;

import com.logihub.model.entity.ParkingCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingCompanyRepository extends JpaRepository<ParkingCompany, Long> {

    Optional<ParkingCompany> findByName(String name);

    List<ParkingCompany> findAllByOrderByNameAsc();

    List<ParkingCompany> findAllByNameContainingIgnoreCase(String name);
}
