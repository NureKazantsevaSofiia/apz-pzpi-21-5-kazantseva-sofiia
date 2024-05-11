package com.logihub.controller;

import com.logihub.exception.TruckException;
import com.logihub.exception.UserException;
import com.logihub.model.request.TruckRequest;
import com.logihub.model.request.UpdateTruckRequest;
import com.logihub.model.response.ShortTruckDTO;
import com.logihub.model.response.TruckDTO;
import com.logihub.service.TruckService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TruckController {

    private final TruckService truckService;

    @PostMapping("/api/truck-manager/{user-id}/trucks")
    public TruckDTO createTruck(Authentication auth,
                                @PathVariable("user-id") Long userId,
                                @RequestBody @Valid TruckRequest truckRequest) throws UserException {
        return truckService.createTruck(auth.getName(), userId, truckRequest);
    }

    @GetMapping("/api/truck-manager/{user-id}/trucks/{truck-id}")
    public TruckDTO getTruck(Authentication auth,
                             @PathVariable("user-id") Long userId,
                             @PathVariable("truck-id") Long truckId)
            throws UserException, TruckException {
        return truckService.getTruck(auth.getName(), userId, truckId);
    }

    @PatchMapping("/api/truck-manager/{user-id}/trucks/{truck-id}")
    public TruckDTO updateTruck(Authentication auth,
                                @PathVariable("user-id") Long userId,
                                @PathVariable("truck-id") Long truckId,
                                @RequestBody @Valid UpdateTruckRequest truckRequest)
            throws UserException, TruckException {
        return truckService.updateTruck(auth.getName(), userId, truckId, truckRequest);
    }

    @Transactional
    @DeleteMapping("/api/truck-manager/{user-id}/trucks/{truck-id}")
    public void deleteTruck(Authentication auth,
                            @PathVariable("user-id") Long userId,
                            @PathVariable("truck-id") Long truckId)
            throws UserException, TruckException {
        truckService.deleteTruck(auth.getName(), userId, truckId);
    }

    @GetMapping("/api/truck-manager/{user-id}/trucks")
    public Page<ShortTruckDTO> getAllTrucksByCompany(Authentication auth,
                                                     @PathVariable("user-id") Long userId,
                                                     Pageable pageable) throws UserException {
        return truckService.getTrucksByCompany(auth.getName(), userId, pageable);
    }

    @GetMapping("/api/truck-manager/{user-id}/trucks/without-manager")
    public Page<ShortTruckDTO> getAllTrucksByCompanyWithoutManager(Authentication auth,
                                                                   @PathVariable("user-id") Long userId,
                                                                   Pageable pageable) throws UserException {
        return truckService.getTrucksByCompanyWithoutManager(auth.getName(), userId, pageable);
    }

    @Transactional
    @PatchMapping("/api/truck-manager/{user-id}/trucks/{truck-id}/remove-manager")
    public TruckDTO removeTruckManager(Authentication auth,
                                       @PathVariable("user-id") Long userId,
                                       @PathVariable("truck-id") Long truckId)
            throws UserException, TruckException {
        return truckService.removeTruckManager(auth.getName(), userId, truckId);
    }

    @Transactional
    @PatchMapping("/api/truck-manager/{user-id}/trucks/{truck-id}/add-manager")
    public TruckDTO addTruckManager(Authentication auth,
                                    @PathVariable("user-id") Long userId,
                                    @PathVariable("truck-id") Long truckId)
            throws UserException, TruckException {
        return truckService.addTruckManager(auth.getName(), userId, truckId);
    }
}
