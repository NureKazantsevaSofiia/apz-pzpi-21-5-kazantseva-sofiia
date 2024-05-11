package com.logihub.repository;

import com.logihub.model.entity.Invoice;
import com.logihub.model.entity.ParkingManager;
import com.logihub.model.entity.TruckManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Page<Invoice> findAllByParkingManager(ParkingManager manager, Pageable pageable);

    Page<Invoice> findAllByTruckManager(TruckManager manager, Pageable pageable);

    Page<Invoice> findAllByTruckManagerAndTruck_NumberContaining(TruckManager manager, String number,
                                                                 Pageable pageable);

    Page<Invoice> findAllByParkingManagerAndParkingPlace_PlaceNumberContaining(ParkingManager manager, String number,
                                                                               Pageable pageable);

    Page<Invoice> findAllByParkingManagerAndSignedByParkingManagerAndSignedByTruckManager(ParkingManager parkingManager,
                                                                                          Boolean signedByParkingManager,
                                                                                          Boolean signedByTruckManager,
                                                                                          Pageable pageable);

    Page<Invoice> findAllByTruckManagerAndSignedByParkingManagerAndSignedByTruckManager(TruckManager truckManager,
                                                                                        Boolean signedByParkingManager,
                                                                                        Boolean signedByTruckManager,
                                                                                        Pageable pageable);
}