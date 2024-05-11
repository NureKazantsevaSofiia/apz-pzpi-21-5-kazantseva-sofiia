package com.logihub.service;

import com.logihub.exception.AuthException;
import com.logihub.exception.UserException;
import com.logihub.model.request.RegisterRequest;
import com.logihub.model.request.UpdateParkingManagerRequest;
import com.logihub.model.response.ParkingManagerDTO;

public interface ParkingManagerService {

    ParkingManagerDTO registerParkingManager(RegisterRequest newParkingManager) throws AuthException;

    ParkingManagerDTO getParkingManager(String email, Long userId) throws UserException;

    ParkingManagerDTO updateParkingManager(String email, Long userId, UpdateParkingManagerRequest parkingManager)
            throws UserException;

    void deleteParkingManager(String email, Long userId) throws UserException;

    ParkingManagerDTO changeParkingManagerCompany(String email, Long userId, Long companyId) throws UserException, AuthException;
}
