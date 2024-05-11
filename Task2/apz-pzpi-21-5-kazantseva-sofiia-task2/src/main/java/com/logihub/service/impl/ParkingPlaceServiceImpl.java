package com.logihub.service.impl;

import com.logihub.exception.ParkingPlaceException;
import com.logihub.exception.UserException;
import com.logihub.model.entity.ParkingPlace;
import com.logihub.model.request.ParkingPlaceRequest;
import com.logihub.model.request.UpdateParkingPlaceRequest;
import com.logihub.model.response.ParkingPlaceDTO;
import com.logihub.model.response.ShortParkingPlaceDTO;
import com.logihub.repository.ParkingPlaceRepository;
import com.logihub.service.ParkingPlaceService;
import com.logihub.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ParkingPlaceServiceImpl implements ParkingPlaceService {

    private final ParkingPlaceRepository parkingPlaceRepository;
    private final AuthUtil authUtil;

    @Override
    public ParkingPlaceDTO createParkingPlace(String email, Long userId, ParkingPlaceRequest parkingPlace)
            throws UserException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        return new ParkingPlaceDTO(parkingPlaceRepository.save(ParkingPlace.builder()
                .parkingManager(parkingManager)
                .placeNumber(parkingPlace.getPlaceNumber())
                .address(parkingPlace.getAddress())
                .hourlyPay(parkingPlace.getHourlyPay())
                .minHeight(parkingPlace.getMinHeight())
                .minWidth(parkingPlace.getMinWidth())
                .minWeight(parkingPlace.getMinWeight())
                .minLength(parkingPlace.getMinLength())
                .maxHeight(parkingPlace.getMaxHeight())
                .maxWidth(parkingPlace.getMaxWidth())
                .maxWeight(parkingPlace.getMaxWeight())
                .maxLength(parkingPlace.getMaxLength())
                .build()
        ));
    }

    @Override
    public ParkingPlaceDTO updateParkingPlace(String email, Long userId, Long placeId,
                                              UpdateParkingPlaceRequest updatedParkingPlace)
            throws UserException, ParkingPlaceException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        var parkingPlace = parkingPlaceRepository.findById(placeId).orElseThrow(
                () -> new ParkingPlaceException(ParkingPlaceException.
                        ParkingPlaceExceptionProfile.PARKING_PLACE_NOT_FOUND)
        );

        if (!Objects.equals(parkingPlace.getParkingManager().getId(), parkingManager.getId())) {
            throw new ParkingPlaceException(ParkingPlaceException.ParkingPlaceExceptionProfile.FORBIDDEN);
        }

        parkingPlace.setHourlyPay(updatedParkingPlace.getHourlyPay());
        parkingPlace.setMinHeight(updatedParkingPlace.getMinHeight());
        parkingPlace.setMinWidth(updatedParkingPlace.getMinWidth());
        parkingPlace.setMinWeight(updatedParkingPlace.getMinWeight());
        parkingPlace.setMinLength(updatedParkingPlace.getMinLength());
        parkingPlace.setMaxHeight(updatedParkingPlace.getMaxHeight());
        parkingPlace.setMaxLength(updatedParkingPlace.getMaxLength());
        parkingPlace.setMaxWidth(updatedParkingPlace.getMaxWidth());
        parkingPlace.setMaxWeight(updatedParkingPlace.getMaxWeight());
        parkingPlace.setHourlyPay(updatedParkingPlace.getHourlyPay());

        return new ParkingPlaceDTO(parkingPlaceRepository.save(parkingPlace));
    }

    @Override
    public void deleteParkingPlace(String email, Long userId, Long placeId)
            throws UserException, ParkingPlaceException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        var parkingPlace = parkingPlaceRepository.findById(placeId).orElseThrow(
                () -> new ParkingPlaceException(ParkingPlaceException.
                        ParkingPlaceExceptionProfile.PARKING_PLACE_NOT_FOUND)
        );

        if (!Objects.equals(parkingPlace.getParkingManager().getId(), parkingManager.getId())) {
            throw new ParkingPlaceException(ParkingPlaceException.ParkingPlaceExceptionProfile.FORBIDDEN);
        }

        parkingPlaceRepository.delete(parkingPlace);
    }

    @Override
    public ParkingPlaceDTO getParkingPlace(String email, Long userId, Long placeId)
            throws UserException, ParkingPlaceException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        var parkingPlace = parkingPlaceRepository.findById(placeId).orElseThrow(
                () -> new ParkingPlaceException(ParkingPlaceException.
                        ParkingPlaceExceptionProfile.PARKING_PLACE_NOT_FOUND)
        );

        if (!Objects.equals(parkingPlace.getParkingManager().getId(), parkingManager.getId())) {
            throw new ParkingPlaceException(ParkingPlaceException.ParkingPlaceExceptionProfile.FORBIDDEN);
        }

        return new ParkingPlaceDTO(parkingPlace);
    }

    @Override
    public Page<ShortParkingPlaceDTO> getAllParkingPlacesByCompany(String email, Long userId, Pageable pageable)
            throws UserException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        return parkingPlaceRepository.findAllByParkingManager_Company(parkingManager.getCompany(), pageable)
                .map(ShortParkingPlaceDTO::new);
    }

    @Override
    public Page<ShortParkingPlaceDTO> getAllParkingPlacesByCompanyWithoutManager(String email, Long userId,
                                                                                 Pageable pageable)
            throws UserException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        return parkingPlaceRepository.findAllByParkingManager_CompanyAndParkingManagerIsNull(
                        parkingManager.getCompany(), pageable)
                .map(ShortParkingPlaceDTO::new);
    }

    @Override
    public ParkingPlaceDTO removeParkingManager(String email, Long userId, Long placeId)
            throws UserException, ParkingPlaceException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        var parkingPlace = parkingPlaceRepository.findById(placeId).orElseThrow(
                () -> new ParkingPlaceException(ParkingPlaceException.
                        ParkingPlaceExceptionProfile.PARKING_PLACE_NOT_FOUND)
        );

        if (!Objects.equals(parkingPlace.getParkingManager().getId(), parkingManager.getId())) {
            throw new ParkingPlaceException(ParkingPlaceException.ParkingPlaceExceptionProfile.FORBIDDEN);
        }

        parkingPlace.setParkingManager(null);

        return new ParkingPlaceDTO(parkingPlaceRepository.save(parkingPlace));
    }

    @Override
    public ParkingPlaceDTO addParkingManager(String email, Long userId, Long placeId)
            throws UserException, ParkingPlaceException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        var parkingPlace = parkingPlaceRepository.findById(placeId).orElseThrow(
                () -> new ParkingPlaceException(ParkingPlaceException.
                        ParkingPlaceExceptionProfile.PARKING_PLACE_NOT_FOUND)
        );

        if (parkingPlace.getParkingManager() != null) {
            throw new ParkingPlaceException(ParkingPlaceException
                    .ParkingPlaceExceptionProfile.PARKING_PLACE_HAS_MANAGER);
        }
        parkingPlace.setParkingManager(parkingManager);

        return new ParkingPlaceDTO(parkingPlaceRepository.save(parkingPlace));
    }
}
