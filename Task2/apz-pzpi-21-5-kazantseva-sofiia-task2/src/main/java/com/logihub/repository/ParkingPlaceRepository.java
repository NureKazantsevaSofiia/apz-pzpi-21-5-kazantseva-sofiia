package com.logihub.repository;

import com.logihub.model.entity.Company;
import com.logihub.model.entity.ParkingPlace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace, Long> {

    Optional<ParkingPlace> findByPlaceNumber(String placeNumber);

    Page<ParkingPlace> findAllByParkingManager_Company(Company company, Pageable pageable);

    Page<ParkingPlace> findAllByParkingManager_CompanyAndParkingManagerIsNull(Company company, Pageable pageable);

    List<ParkingPlace> findAllByParkingManager_Company(Company company);
}
