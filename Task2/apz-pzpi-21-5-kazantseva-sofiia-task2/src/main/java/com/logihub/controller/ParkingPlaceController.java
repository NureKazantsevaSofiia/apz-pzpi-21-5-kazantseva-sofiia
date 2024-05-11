package com.logihub.controller;

import com.logihub.exception.ParkingPlaceException;
import com.logihub.exception.UserException;
import com.logihub.model.request.ParkingPlaceRequest;
import com.logihub.model.request.UpdateParkingPlaceRequest;
import com.logihub.model.response.ParkingPlaceDTO;
import com.logihub.model.response.ShortParkingPlaceDTO;
import com.logihub.service.ParkingPlaceService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ParkingPlaceController {

    private final ParkingPlaceService parkingPlaceService;

    @PostMapping("/api/parking-manager/{user-id}/parking-places")
    public ParkingPlaceDTO createParkingPlace(Authentication auth,
                                              @PathVariable("user-id") Long userId,
                                              @RequestBody @Valid ParkingPlaceRequest parkingPlaceRequest) throws UserException {
        return parkingPlaceService.createParkingPlace(auth.getName(), userId, parkingPlaceRequest);
    }

    @GetMapping("/api/parking-manager/{user-id}/parking-places/{parking-place-id}")
    public ParkingPlaceDTO getParkingPlace(Authentication auth,
                                           @PathVariable("user-id") Long userId,
                                           @PathVariable("parking-place-id") Long placeId)
            throws UserException, ParkingPlaceException {
        return parkingPlaceService.getParkingPlace(auth.getName(), userId, placeId);
    }

    @PatchMapping("/api/parking-manager/{user-id}/parking-places/{parking-place-id}")
    public ParkingPlaceDTO updateParkingPlace(Authentication auth,
                                              @PathVariable("user-id") Long userId,
                                              @PathVariable("parking-place-id") Long placeId,
                                              @RequestBody @Valid UpdateParkingPlaceRequest parkingPlaceRequest)
            throws UserException, ParkingPlaceException {
        return parkingPlaceService.updateParkingPlace(auth.getName(), userId, placeId, parkingPlaceRequest);
    }

    @Transactional
    @DeleteMapping("/api/parking-manager/{user-id}/parking-places/{parking-place-id}")
    public void deleteParkingPlace(Authentication auth,
                                   @PathVariable("user-id") Long userId,
                                   @PathVariable("parking-place-id") Long placeId)
            throws UserException, ParkingPlaceException {
        parkingPlaceService.deleteParkingPlace(auth.getName(), userId, placeId);
    }

    @GetMapping("/api/parking-manager/{user-id}/parking-places")
    public Page<ShortParkingPlaceDTO> getAllParkingPlaceByCompany(Authentication auth,
                                                                  @PathVariable("user-id") Long userId,
                                                                  Pageable pageable) throws UserException {
        return parkingPlaceService.getAllParkingPlacesByCompany(auth.getName(), userId, pageable);
    }

    @GetMapping("/api/parking-manager/{user-id}/parking-places/without-manager")
    public Page<ShortParkingPlaceDTO> getAllParkingPlaceByCompanyWithoutManager(Authentication auth,
                                                                                @PathVariable("user-id") Long userId,
                                                                                Pageable pageable)
            throws UserException {
        return parkingPlaceService.getAllParkingPlacesByCompanyWithoutManager(auth.getName(), userId, pageable);
    }

    @Transactional
    @PatchMapping("/api/parking-manager/{user-id}/parking-places/{parking-place-id}/remove-manager")
    public void removeParkingManager(Authentication auth,
                                     @PathVariable("user-id") Long userId,
                                     @PathVariable("parking-place-id") Long placeId)
            throws UserException, ParkingPlaceException {
        parkingPlaceService.removeParkingManager(auth.getName(), userId, placeId);
    }

    @Transactional
    @PatchMapping("/api/parking-manager/{user-id}/parking-places/{parking-place-id}/add-manager")
    public ParkingPlaceDTO addParkingManager(Authentication auth,
                                             @PathVariable("user-id") Long userId,
                                             @PathVariable("parking-place-id") Long placeId)
            throws UserException, ParkingPlaceException {
        return parkingPlaceService.addParkingManager(auth.getName(), userId, placeId);
    }
}
