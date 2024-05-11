package com.logihub.service;

import com.logihub.exception.AuthException;
import com.logihub.exception.TruckException;
import com.logihub.exception.UserException;
import com.logihub.model.request.RegisterRequest;
import com.logihub.model.request.UpdateTruckManagerRequest;
import com.logihub.model.response.ParkingPlaceDTO;
import com.logihub.model.response.TruckManagerDTO;

public interface TruckManagerService {

    TruckManagerDTO registerTruckManager(RegisterRequest newTruckManager) throws AuthException;

    TruckManagerDTO getTruckManager(String email, Long userId) throws UserException;

    TruckManagerDTO updateTruckManager(String email, Long userId, UpdateTruckManagerRequest truckManager)
            throws UserException;

    void deleteTruckManager(String email, Long userId) throws UserException;

    TruckManagerDTO changeTruckManagerCompany(String email, Long userId, Long companyId) throws UserException, AuthException;

    ParkingPlaceDTO searchParkingPlace(String email, Long userId, Long truckId, Long companyId) throws UserException, AuthException, TruckException;
}
