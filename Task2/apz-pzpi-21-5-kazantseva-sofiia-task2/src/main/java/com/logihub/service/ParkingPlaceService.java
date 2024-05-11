package com.logihub.service;

import com.logihub.exception.ParkingPlaceException;
import com.logihub.exception.UserException;
import com.logihub.model.request.ParkingPlaceRequest;
import com.logihub.model.request.UpdateParkingPlaceRequest;
import com.logihub.model.response.ParkingPlaceDTO;
import com.logihub.model.response.ShortParkingPlaceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParkingPlaceService {

    ParkingPlaceDTO createParkingPlace(String email, Long userId, ParkingPlaceRequest parkingPlace)
            throws UserException;

    ParkingPlaceDTO updateParkingPlace(String email, Long userId, Long placeId,
                                       UpdateParkingPlaceRequest parkingPlace)
            throws UserException, ParkingPlaceException;

    void deleteParkingPlace(String email, Long userId, Long placeId) throws UserException, ParkingPlaceException;

    ParkingPlaceDTO getParkingPlace(String email, Long userId, Long placeId) throws UserException, ParkingPlaceException;

    Page<ShortParkingPlaceDTO> getAllParkingPlacesByCompany(String email, Long userId,
                                                            Pageable pageable) throws UserException;

    Page<ShortParkingPlaceDTO> getAllParkingPlacesByCompanyWithoutManager(String email, Long userId,
                                                                          Pageable pageable) throws UserException;

    ParkingPlaceDTO removeParkingManager(String email, Long userId, Long placeId) throws UserException,
            ParkingPlaceException;

    ParkingPlaceDTO addParkingManager(String email, Long userId, Long placeId) throws UserException,
            ParkingPlaceException;
}
