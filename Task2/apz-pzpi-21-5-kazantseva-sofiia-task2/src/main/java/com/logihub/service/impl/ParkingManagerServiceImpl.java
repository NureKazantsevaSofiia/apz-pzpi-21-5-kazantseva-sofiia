package com.logihub.service.impl;

import com.logihub.exception.AuthException;
import com.logihub.exception.UserException;
import com.logihub.model.entity.ParkingManager;
import com.logihub.model.enums.Role;
import com.logihub.model.request.RegisterRequest;
import com.logihub.model.request.UpdateParkingManagerRequest;
import com.logihub.model.response.ParkingManagerDTO;
import com.logihub.repository.ParkingCompanyRepository;
import com.logihub.repository.ParkingManagerRepository;
import com.logihub.repository.UserRepository;
import com.logihub.service.ParkingManagerService;
import com.logihub.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ParkingManagerServiceImpl implements ParkingManagerService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ParkingManagerRepository parkingManagerRepository;
    private final ParkingCompanyRepository parkingCompanyRepository;
    private final AuthUtil authUtil;

    @Override
    public ParkingManagerDTO registerParkingManager(RegisterRequest newParkingManager) throws AuthException {

        if (userRepository.existsByEmailIgnoreCase(newParkingManager.getEmail())) {
            throw new AuthException(AuthException.AuthExceptionProfile.EMAIL_OCCUPIED);
        }

        var company = parkingCompanyRepository.findById(newParkingManager.getCompanyId()).orElseThrow(
                () -> new AuthException(AuthException.AuthExceptionProfile.COMPANY_NOT_FOUND)
        );

        ParkingManager parkingManager = ParkingManager.builder()
                .email(newParkingManager.getEmail())
                .firstName(newParkingManager.getFirstName())
                .lastName(newParkingManager.getLastName())
                .password(passwordEncoder.encode(newParkingManager.getPassword()))
                .role(Role.PARKING_MANAGER)
                .company(company)
                .build();

        return new ParkingManagerDTO(parkingManagerRepository.save(parkingManager));
    }

    @Override
    public ParkingManagerDTO getParkingManager(String email, Long userId) throws UserException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        return new ParkingManagerDTO(parkingManager);
    }

    @Override
    public ParkingManagerDTO updateParkingManager(String email, Long userId,
                                                  UpdateParkingManagerRequest updatedParkingManager)
            throws UserException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        parkingManager.setFirstName(updatedParkingManager.getFirstName());
        parkingManager.setLastName(updatedParkingManager.getLastName());
        parkingManager.setAvatar(updatedParkingManager.getAvatar());

        return new ParkingManagerDTO(parkingManagerRepository.save(parkingManager));
    }

    @Override
    public void deleteParkingManager(String email, Long userId) throws UserException {
        authUtil.findParkingManagerByEmailAndIdAndCheckByAdmin(email, userId);

        parkingManagerRepository.deleteById(userId);
    }

    @Override
    public ParkingManagerDTO changeParkingManagerCompany(String email, Long userId, Long companyId)
            throws UserException, AuthException {
        var parkingManager = authUtil.findParkingManagerByEmailAndId(email, userId);

        var company = parkingCompanyRepository.findById(companyId).orElseThrow(
                () -> new AuthException(AuthException.AuthExceptionProfile.COMPANY_NOT_FOUND)
        );

        parkingManager.setCompany(company);

        return new ParkingManagerDTO(parkingManagerRepository.save(parkingManager));
    }
}
