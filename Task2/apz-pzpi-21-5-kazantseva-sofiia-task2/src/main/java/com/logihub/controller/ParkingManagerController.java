package com.logihub.controller;

import com.logihub.exception.AuthException;
import com.logihub.exception.UserException;
import com.logihub.model.request.RegisterRequest;
import com.logihub.model.request.UpdateParkingManagerRequest;
import com.logihub.model.response.ParkingManagerDTO;
import com.logihub.service.ParkingManagerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ParkingManagerController {

    private ParkingManagerService parkingManagerService;

    @PostMapping("/api/parking-manager/register")
    public ParkingManagerDTO register(@Valid @RequestBody RegisterRequest newParkingManager)
            throws AuthException {
        return parkingManagerService.registerParkingManager(newParkingManager);
    }

    @GetMapping("/api/parking-manager/{user-id}")
    public ParkingManagerDTO getParkingManager(Authentication auth,
                                               @PathVariable("user-id") Long userId) throws UserException {
        return parkingManagerService.getParkingManager(auth.getName(), userId);
    }

    @PatchMapping("/api/parking-manager/{user-id}")
    public ParkingManagerDTO updateParkingManager(Authentication auth,
                                                  @PathVariable("user-id") Long userId,
                                                  @RequestBody @Valid UpdateParkingManagerRequest parkingManager)
            throws UserException {
        return parkingManagerService.updateParkingManager(auth.getName(), userId, parkingManager);
    }

    @Transactional
    @DeleteMapping("/api/parking-manager/{user-id}")
    public void deleteParkingManager(Authentication auth,
                                     @PathVariable("user-id") Long userId) throws UserException {
        parkingManagerService.deleteParkingManager(auth.getName(), userId);
    }

    @Transactional
    @PatchMapping("/api/parking-manager/{user-id}/company/{company-id}")
    public ParkingManagerDTO changeParkingManagerCompany(Authentication auth,
                                                         @PathVariable("user-id") Long userId,
                                                         @PathVariable("company-id") Long companyId)
            throws UserException, AuthException {
        return parkingManagerService.changeParkingManagerCompany(auth.getName(), userId, companyId);
    }
}
