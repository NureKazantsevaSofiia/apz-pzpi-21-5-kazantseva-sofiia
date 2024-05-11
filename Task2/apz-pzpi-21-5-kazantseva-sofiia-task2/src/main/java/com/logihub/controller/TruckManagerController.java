package com.logihub.controller;

import com.logihub.exception.AuthException;
import com.logihub.exception.TruckException;
import com.logihub.exception.UserException;
import com.logihub.model.request.RegisterRequest;
import com.logihub.model.request.UpdateTruckManagerRequest;
import com.logihub.model.response.ParkingPlaceDTO;
import com.logihub.model.response.TruckManagerDTO;
import com.logihub.service.TruckManagerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TruckManagerController {

    private TruckManagerService truckManagerService;

    @PostMapping("/api/truck-manager/register")
    public TruckManagerDTO register(@Valid @RequestBody RegisterRequest newTruckManager)
            throws AuthException {
        return truckManagerService.registerTruckManager(newTruckManager);
    }

    @GetMapping("/api/truck-manager/{user-id}")
    public TruckManagerDTO getTruckManager(Authentication auth,
                                           @PathVariable("user-id") Long userId) throws UserException {
        return truckManagerService.getTruckManager(auth.getName(), userId);
    }

    @PatchMapping("/api/truck-manager/{user-id}")
    public TruckManagerDTO updateTruckManager(Authentication auth,
                                              @PathVariable("user-id") Long userId,
                                              @RequestBody @Valid UpdateTruckManagerRequest truckManager)
            throws UserException {
        return truckManagerService.updateTruckManager(auth.getName(), userId, truckManager);
    }

    @Transactional
    @DeleteMapping("/api/truck-manager/{user-id}")
    public void deleteTruckManager(Authentication auth,
                                   @PathVariable("user-id") Long userId) throws UserException {
        truckManagerService.deleteTruckManager(auth.getName(), userId);
    }

    @Transactional
    @PatchMapping("/api/truck-manager/{user-id}/company/{company-id}")
    public TruckManagerDTO changeTruckManagerCompany(Authentication auth,
                                                     @PathVariable("user-id") Long userId,
                                                     @PathVariable("company-id") Long companyId)
            throws UserException, AuthException {
        return truckManagerService.changeTruckManagerCompany(auth.getName(), userId, companyId);
    }

    @GetMapping("/api/truck-manager/{user-id}/trucks/{truck-id}/companies/{company-id}/search-place")
    public ParkingPlaceDTO searchParkingPlace(Authentication auth,
                                              @PathVariable("user-id") Long userId,
                                              @PathVariable("truck-id") Long truckId,
                                              @PathVariable("company-id") Long companyId)
            throws UserException, AuthException, TruckException {
        return truckManagerService.searchParkingPlace(auth.getName(), userId, truckId, companyId);
    }
}
