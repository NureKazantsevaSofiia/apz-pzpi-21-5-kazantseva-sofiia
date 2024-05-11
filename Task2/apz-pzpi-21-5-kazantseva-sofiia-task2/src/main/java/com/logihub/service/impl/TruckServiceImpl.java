package com.logihub.service.impl;

import com.logihub.exception.TruckException;
import com.logihub.exception.UserException;
import com.logihub.model.entity.Truck;
import com.logihub.model.request.TruckRequest;
import com.logihub.model.request.UpdateTruckRequest;
import com.logihub.model.response.ShortTruckDTO;
import com.logihub.model.response.TruckDTO;
import com.logihub.model.response.TruckManagerDTO;
import com.logihub.repository.TruckRepository;
import com.logihub.service.TruckService;
import com.logihub.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class TruckServiceImpl implements TruckService {

    private final TruckRepository truckRepository;
    private AuthUtil authUtil;

    @Override
    public TruckDTO createTruck(String email, Long userId, TruckRequest truckRequest) throws UserException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        return toTruckDTO(truckRepository.save(Truck.builder()
                .truckManager(truckManager)
                .number(truckRequest.getNumber())
                .height(truckRequest.getHeight())
                .width(truckRequest.getWidth())
                .weight(truckRequest.getWeight())
                .length(truckRequest.getLength())
                .build()
        ));
    }

    @Override
    public TruckDTO updateTruck(String email, Long userId, Long truckId, UpdateTruckRequest truckRequest)
            throws UserException, TruckException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        var truck = truckRepository.findById(truckId).orElseThrow(
                () -> new TruckException(TruckException.TruckExceptionProfile.TRUCK_NOT_FOUND)
        );

        if (!Objects.equals(truck.getTruckManager().getId(), truckManager.getId())) {
            throw new TruckException(TruckException.TruckExceptionProfile.FORBIDDEN);
        }

        truck.setHeight(truckRequest.getHeight());
        truck.setWidth(truckRequest.getWidth());
        truck.setWeight(truckRequest.getWeight());
        truck.setLength(truckRequest.getLength());

        return toTruckDTO(truckRepository.save(truck));
    }

    @Override
    public void deleteTruck(String email, Long userId, Long truckId) throws UserException, TruckException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        var truck = truckRepository.findById(truckId).orElseThrow(
                () -> new TruckException(TruckException.TruckExceptionProfile.TRUCK_NOT_FOUND)
        );

        if (!Objects.equals(truck.getTruckManager().getId(), truckManager.getId())) {
            throw new TruckException(TruckException.TruckExceptionProfile.FORBIDDEN);
        }

        truckRepository.delete(truck);
    }

    @Override
    public TruckDTO getTruck(String email, Long userId, Long truckId) throws UserException, TruckException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        var truck = truckRepository.findById(truckId).orElseThrow(
                () -> new TruckException(TruckException.TruckExceptionProfile.TRUCK_NOT_FOUND)
        );

        if (!Objects.equals(truck.getTruckManager().getId(), truckManager.getId())) {
            throw new TruckException(TruckException.TruckExceptionProfile.FORBIDDEN);
        }

        return toTruckDTO(truck);
    }

    @Override
    public Page<ShortTruckDTO> getTrucksByCompany(String email, Long userId, Pageable pageable)
            throws UserException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        return truckRepository.findAllByTruckManager_Company(truckManager.getCompany(), pageable)
                .map(ShortTruckDTO::new);
    }

    @Override
    public Page<ShortTruckDTO> getTrucksByCompanyWithoutManager(String email, Long userId, Pageable pageable)
            throws UserException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        return truckRepository.findAllByTruckManager_CompanyAndTruckManagerIsNull(
                truckManager.getCompany(), pageable).map(ShortTruckDTO::new);
    }

    @Override
    public TruckDTO removeTruckManager(String email, Long userId, Long truckId)
            throws TruckException, UserException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        var truck = truckRepository.findById(truckId).orElseThrow(
                () -> new TruckException(TruckException.TruckExceptionProfile.TRUCK_NOT_FOUND)
        );

        if (!Objects.equals(truck.getTruckManager().getId(), truckManager.getId())) {
            throw new TruckException(TruckException.TruckExceptionProfile.FORBIDDEN);
        }

        truck.setTruckManager(null);

        return toTruckDTO(truckRepository.save(truck));
    }

    @Override
    public TruckDTO addTruckManager(String email, Long userId, Long truckId) throws UserException, TruckException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        var truck = truckRepository.findById(truckId).orElseThrow(
                () -> new TruckException(TruckException.TruckExceptionProfile.TRUCK_NOT_FOUND)
        );

        if (truck.getTruckManager() != null) {
            throw new TruckException(TruckException.TruckExceptionProfile.TRUCK_HAS_MANAGER);
        }

        truck.setTruckManager(truckManager);

        return toTruckDTO(truckRepository.save(truck));
    }

    private TruckDTO toTruckDTO(Truck truck) {
        TruckManagerDTO manager = null;
        if (truck.getTruckManager() != null) {
            manager = new TruckManagerDTO(truck.getTruckManager());
        }
        return TruckDTO.builder()
                .id(truck.getId())
                .number(truck.getNumber())
                .height(truck.getHeight())
                .width(truck.getWidth())
                .weight(truck.getWeight())
                .length(truck.getLength())
                .truckManager(manager)
                .build();
    }
}
