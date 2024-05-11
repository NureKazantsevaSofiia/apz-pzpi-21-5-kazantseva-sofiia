package com.logihub.service.impl;

import com.logihub.exception.AuthException;
import com.logihub.exception.TruckException;
import com.logihub.exception.UserException;
import com.logihub.model.entity.ParkingPlace;
import com.logihub.model.entity.Truck;
import com.logihub.model.entity.TruckManager;
import com.logihub.model.enums.Role;
import com.logihub.model.request.RegisterRequest;
import com.logihub.model.request.UpdateTruckManagerRequest;
import com.logihub.model.response.ParkingPlaceDTO;
import com.logihub.model.response.TruckManagerDTO;
import com.logihub.repository.*;
import com.logihub.service.TruckManagerService;
import com.logihub.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TruckManagerServiceImpl implements TruckManagerService {

    private final UserRepository userRepository;
    private final TruckManagerRepository truckManagerRepository;
    private final TruckCompanyRepository truckCompanyRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;
    private final ParkingCompanyRepository parkingCompanyRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;
    private final TruckRepository truckRepository;

    @Override
    public TruckManagerDTO registerTruckManager(RegisterRequest newTruckManager) throws AuthException {
        if (userRepository.existsByEmailIgnoreCase(newTruckManager.getEmail())) {
            throw new AuthException(AuthException.AuthExceptionProfile.EMAIL_OCCUPIED);
        }

        var company = truckCompanyRepository.findById(newTruckManager.getCompanyId()).orElseThrow(
                () -> new AuthException(AuthException.AuthExceptionProfile.COMPANY_NOT_FOUND)
        );

        TruckManager truckManager = TruckManager.builder()
                .email(newTruckManager.getEmail())
                .firstName(newTruckManager.getFirstName())
                .lastName(newTruckManager.getLastName())
                .password(passwordEncoder.encode(newTruckManager.getPassword()))
                .role(Role.TRUCK_MANAGER)
                .company(company)
                .build();

        return new TruckManagerDTO(truckManagerRepository.save(truckManager));
    }

    @Override
    public TruckManagerDTO getTruckManager(String email, Long userId) throws UserException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);
        return new TruckManagerDTO(truckManager);
    }

    @Override
    public TruckManagerDTO updateTruckManager(String email, Long userId,
                                              UpdateTruckManagerRequest updatedTruckManager) throws UserException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        truckManager.setFirstName(updatedTruckManager.getFirstName());
        truckManager.setLastName(updatedTruckManager.getLastName());
        truckManager.setAvatar(updatedTruckManager.getAvatar());

        return new TruckManagerDTO(truckManagerRepository.save(truckManager));
    }

    @Override
    public void deleteTruckManager(String email, Long userId) throws UserException {
        authUtil.findTruckManagerByEmailAndIdAndCheckByAdmin(email, userId);

        truckManagerRepository.deleteById(userId);
    }

    @Override
    public TruckManagerDTO changeTruckManagerCompany(String email, Long userId, Long companyId)
            throws UserException, AuthException {
        var truckManager = authUtil.findTruckManagerByEmailAndId(email, userId);

        var company = truckCompanyRepository.findById(companyId).orElseThrow(
                () -> new AuthException(AuthException.AuthExceptionProfile.COMPANY_NOT_FOUND)
        );

        truckManager.setCompany(company);

        return new TruckManagerDTO(truckManagerRepository.save(truckManager));
    }

    @Override
    public ParkingPlaceDTO searchParkingPlace(String email, Long userId, Long truckId, Long companyId)
            throws UserException, AuthException, TruckException {
        authUtil.findTruckManagerByEmailAndId(email, userId);

        var truck = truckRepository.findById(truckId).orElseThrow(
                () -> new TruckException(TruckException.TruckExceptionProfile.TRUCK_NOT_FOUND)
        );

        var company = parkingCompanyRepository.findById(companyId).orElseThrow(
                () -> new AuthException(AuthException.AuthExceptionProfile.COMPANY_NOT_FOUND)
        );

        List<ParkingPlace> places = parkingPlaceRepository.findAllByParkingManager_Company(company);

        ParkingPlace bestPlace = null;
        double maxEntryAngle = Double.NEGATIVE_INFINITY;

        for (ParkingPlace place : places) {
            if (canFit(truck, place)) {
                double entryAngle = calculateEntryAngle(truck, place);
                if (entryAngle > maxEntryAngle) {
                    maxEntryAngle = entryAngle;
                    bestPlace = place;
                }
            }
        }

        if (bestPlace == null) {
            return null;
        }

        return new ParkingPlaceDTO(bestPlace);
    }

    public boolean canFit(Truck truck, ParkingPlace parkingPlace) {
        return truck.getHeight() >= parkingPlace.getMinHeight() &&
                truck.getHeight() <= parkingPlace.getMinHeight() &&
                truck.getLength() >= parkingPlace.getMinLength() &&
                truck.getLength() <= parkingPlace.getMinLength() &&
                truck.getWidth() >= parkingPlace.getMinWidth() &&
                truck.getWidth() <= parkingPlace.getMinWidth() &&
                truck.getWeight() >= parkingPlace.getMinWeight() &&
                truck.getWeight() <= parkingPlace.getMinWeight();
    }

    public double calculateEntryAngle(Truck truck, ParkingPlace parkingPlace) {
        double availableWidth = Math.min(truck.getWidth(), parkingPlace.getMaxWidth()) - parkingPlace.getMinWidth();
        double availableLength = Math.min(truck.getLength(), parkingPlace.getMaxLength()) - parkingPlace.getMinLength();
        return Math.atan(availableWidth / availableLength);
    }
}
