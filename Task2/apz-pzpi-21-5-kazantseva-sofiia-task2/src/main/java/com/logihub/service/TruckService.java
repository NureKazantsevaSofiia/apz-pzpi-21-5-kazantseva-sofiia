package com.logihub.service;

import com.logihub.exception.TruckException;
import com.logihub.exception.UserException;
import com.logihub.model.request.TruckRequest;
import com.logihub.model.request.UpdateTruckRequest;
import com.logihub.model.response.ShortTruckDTO;
import com.logihub.model.response.TruckDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TruckService {

    TruckDTO createTruck(String email, Long userId, TruckRequest truckRequest) throws UserException;

    TruckDTO updateTruck(String email, Long userId, Long truckId, UpdateTruckRequest truckRequest)
            throws UserException, TruckException;

    void deleteTruck(String email, Long userId, Long truckId) throws UserException, TruckException;

    TruckDTO getTruck(String email, Long userId, Long truckId) throws UserException, TruckException;

    Page<ShortTruckDTO> getTrucksByCompany(String email, Long userId, Pageable pageable) throws UserException;

    Page<ShortTruckDTO> getTrucksByCompanyWithoutManager(String email, Long userId, Pageable pageable)
            throws UserException;

    TruckDTO removeTruckManager(String email, Long userId, Long truckId) throws TruckException, UserException;

    TruckDTO addTruckManager(String email, Long userId, Long truckId) throws UserException, TruckException;
}
