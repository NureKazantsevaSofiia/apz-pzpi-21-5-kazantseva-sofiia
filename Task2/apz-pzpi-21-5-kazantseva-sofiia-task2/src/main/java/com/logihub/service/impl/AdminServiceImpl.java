package com.logihub.service.impl;

import com.logihub.exception.AuthException;
import com.logihub.exception.DatabaseException;
import com.logihub.exception.UserException;
import com.logihub.model.entity.Admin;
import com.logihub.model.enums.Role;
import com.logihub.model.request.UpdateAdminRequest;
import com.logihub.model.response.AdminDTO;
import com.logihub.model.response.DatabaseHistoryDTO;
import com.logihub.model.response.ParkingManagerDTO;
import com.logihub.model.response.TruckManagerDTO;
import com.logihub.repository.AdminRepository;
import com.logihub.repository.ParkingManagerRepository;
import com.logihub.repository.TruckManagerRepository;
import com.logihub.repository.UserRepository;
import com.logihub.service.*;
import com.logihub.util.AuthUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private AdminRepository adminRepository;
    private TruckManagerRepository truckManagerRepository;
    private ParkingManagerRepository parkingManagerRepository;

    private PasswordEncoder passwordEncoder;
    private AuthUtil authUtil;

    private TruckManagerService truckManagerService;
    private ParkingManagerService parkingManagerService;
    private DatabaseHistoryService databaseHistoryService;
    private FileService fileService;


    @Override
    public AdminDTO getAdmin(String email, Long adminId) throws UserException {
        return new AdminDTO(authUtil.checkAdminByEmailAndId(email, adminId));
    }

    @Override
    public void addAdmin(String email, Long adminId, String newAdminEmail)
            throws UserException, SecurityException, AuthException {
        var admin = authUtil.checkAdminByEmailAndId(email, adminId);

        var user = userRepository.findByEmailIgnoreCase(newAdminEmail).orElse(null);

        if (user != null) {
            throw new AuthException(AuthException.AuthExceptionProfile.EMAIL_OCCUPIED);
        }

        Admin newAdmin = Admin.builder()
                .email(newAdminEmail)
                .role(Role.ADMIN)
                .addedBy(admin.getEmail())
                .dateOfAdding(LocalDateTime.now())
                .approved(false)
                .newAccount(true)
                .build();

        adminRepository.save(newAdmin);
    }

    @Override
    public String approveAdmin(String email, Long adminId, Long newAdminId) throws UserException {
        authUtil.checkAdminByEmailAndChief(email, adminId);

        var newAdmin = adminRepository.findById(newAdminId).orElseThrow(
                () -> new UserException(UserException.UserExceptionProfile.ADMIN_NOT_FOUND));

        if (Role.CHIEF_ADMIN.equals(newAdmin.getRole())) {
            throw new UserException(UserException.UserExceptionProfile.PERMISSION_DENIED);
        }

        var password = RandomStringUtils.random(7, true, true);

        newAdmin.setApproved(true);
        newAdmin.setDateOfApproving(LocalDateTime.now());
        newAdmin.setPassword(passwordEncoder.encode(password));

        adminRepository.save(newAdmin);

        return password;
    }

    @Override
    public void declineAdmin(String email, Long adminId, Long newAdminId) throws UserException {
        authUtil.checkAdminByEmailAndChief(email, adminId);

        var newAdmin = adminRepository.findById(newAdminId).orElseThrow(
                () -> new UserException(UserException.UserExceptionProfile.ADMIN_NOT_FOUND));

        if (Role.CHIEF_ADMIN.equals(newAdmin.getRole())) {
            throw new UserException(UserException.UserExceptionProfile.PERMISSION_DENIED);
        }

        adminRepository.delete(newAdmin);
    }

    @Override
    public void updateAdmin(String email, Long adminId, UpdateAdminRequest updatedAdmin) throws UserException {
        var admin = authUtil.checkAdminByEmailAndId(email, adminId);

        if (!admin.isApproved()) {
            throw new UserException(UserException.UserExceptionProfile.SOMETHING_WRONG);
        }

        admin.setAvatar(updatedAdmin.getAvatar());
        admin.setFirstName(updatedAdmin.getFirstName());
        admin.setLastName(updatedAdmin.getLastName());
        admin.setPassword(passwordEncoder.encode(updatedAdmin.getPassword()));
        admin.setNewAccount(false);

        adminRepository.save(admin);
    }

    @Override
    public List<AdminDTO> getApprovedAdmins(String email) throws UserException {
        var admin = adminRepository.findByEmailIgnoreCase(email).orElseThrow(
                () -> new UserException(UserException.UserExceptionProfile.ADMIN_NOT_FOUND));

        return adminRepository.findAllByApprovedTrueOrderByDateOfApproving().stream()
                .filter(a -> a.getRole().equals(Role.ADMIN) && !a.getEmail().equals(admin.getEmail()))
                .map(AdminDTO::new)
                .toList();
    }

    @Override
    public List<AdminDTO> getNotApprovedAdmins(String email) throws UserException {
        var admin = adminRepository.findByEmailIgnoreCase(email).orElseThrow(
                () -> new UserException(UserException.UserExceptionProfile.ADMIN_NOT_FOUND));

        return adminRepository.findAllByApprovedFalseOrderByDateOfAdding().stream()
                .filter(a -> a.getRole().equals(Role.ADMIN) && !a.getEmail().equals(admin.getEmail()))
                .map(AdminDTO::new)
                .toList();
    }

    @Override
    public void deleteTruckManager(String email, Long adminId, Long userId)
            throws UserException {
        authUtil.checkAdminByEmailAndId(email, adminId);

        var truckManager = truckManagerRepository.findById(userId)
                .orElseThrow(
                        () -> new UserException(UserException.UserExceptionProfile.TRUCK_MANAGER_NOT_FOUND)
                );

        truckManagerService.deleteTruckManager(email, truckManager.getId());
    }

    @Override
    public void deleteParkingManager(String email, Long adminId, Long userId)
            throws UserException {
        authUtil.checkAdminByEmailAndId(email, adminId);

        var parkingManager = parkingManagerRepository.findById(userId)
                .orElseThrow(
                        () -> new UserException(UserException.UserExceptionProfile.PARKING_MANAGER_NOT_FOUND)
                );

        parkingManagerService.deleteParkingManager(email, parkingManager.getId());
    }

    @Override
    public List<TruckManagerDTO> getTruckManagers(String email, Long adminId) throws UserException {
        authUtil.checkAdminByEmailAndId(email, adminId);

        return truckManagerRepository.findAll().stream().map(TruckManagerDTO::new).toList();
    }

    @Override
    public List<ParkingManagerDTO> getParkingManagers(String email, Long adminId) throws UserException {
        authUtil.checkAdminByEmailAndId(email, adminId);

        return parkingManagerRepository.findAll().stream().map(ParkingManagerDTO::new).toList();
    }

    @Override
    public List<DatabaseHistoryDTO> getWeekDatabaseHistory(String email, Long adminId) throws UserException {
        authUtil.checkAdminByEmailAndChief(email, adminId);

        return databaseHistoryService.getWeekDatabaseHistory();
    }

    @Override
    public void exportDatabase(String email, Long adminId, HttpServletResponse response)
            throws SQLException, IOException, ClassNotFoundException, UserException {
        var admin = authUtil.checkAdminByEmailAndId(email, adminId);

        databaseHistoryService.saveExportOperation(admin);

        fileService.generateDatabaseZipDump(response);
    }

    @Override
    public void importDatabase(String email, Long adminId, MultipartFile file)
            throws IOException, UserException, DatabaseException {
        var admin = authUtil.checkAdminByEmailAndId(email, adminId);
        if (file == null || file.isEmpty()) {
            throw new DatabaseException(DatabaseException.DatabaseExceptionProfile.INVALID_FILE);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (!"sql".equals(extension)) {
            throw new DatabaseException(DatabaseException.DatabaseExceptionProfile.INVALID_FILE_EXTENSION);
        }

        String sql = new String(file.getBytes());

        databaseHistoryService.saveImportOperation(admin);

        try {
            fileService.importDatabase(sql);
        } catch (Exception exception) {
            throw new DatabaseException(DatabaseException.DatabaseExceptionProfile.DATABASE_EXCEPTION);
        }
    }
}

