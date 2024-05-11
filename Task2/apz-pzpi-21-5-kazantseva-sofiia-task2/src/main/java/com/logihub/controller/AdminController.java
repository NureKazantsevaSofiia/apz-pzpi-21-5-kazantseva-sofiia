package com.logihub.controller;

import com.logihub.exception.AuthException;
import com.logihub.exception.DatabaseException;
import com.logihub.exception.UserException;
import com.logihub.model.request.UpdateAdminRequest;
import com.logihub.model.response.AdminDTO;
import com.logihub.model.response.DatabaseHistoryDTO;
import com.logihub.model.response.ParkingManagerDTO;
import com.logihub.model.response.TruckManagerDTO;
import com.logihub.service.AdminService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/api/admins/{admin-id}")
    public AdminDTO getAdmin(Authentication auth,
                             @PathVariable("admin-id") Long adminId) throws UserException {
        return adminService.getAdmin(auth.getName(), adminId);
    }

    @GetMapping("/api/admins/{admin-id}/truck-managers")
    public List<TruckManagerDTO> getTruckManagers(Authentication auth,
                                                  @PathVariable("admin-id") Long adminId) throws UserException {
        return adminService.getTruckManagers(auth.getName(), adminId);
    }

    @GetMapping("/api/admins/{admin-id}/parking-managers")
    public List<ParkingManagerDTO> getParkingManagers(Authentication auth,
                                                      @PathVariable("admin-id") Long adminId) throws UserException {
        return adminService.getParkingManagers(auth.getName(), adminId);
    }

    @Transactional
    @DeleteMapping("/api/admins/{admin-id}/truck-managers/{truck-manager-id}")
    public void deleteTruckManager(Authentication auth,
                                   @PathVariable("admin-id") Long adminId,
                                   @PathVariable("truck-manager-id") Long driverId)
            throws SecurityException, UserException {
        adminService.deleteTruckManager(auth.getName(), adminId, driverId);
    }

    @Transactional
    @DeleteMapping("/api/admins/{admin-id}/parking-managers/{parking-manager-id}")
    public void deleteParkingManager(Authentication auth,
                                     @PathVariable("admin-id") Long adminId,
                                     @PathVariable("parking-manager-id") Long managerId)
            throws SecurityException, UserException {
        adminService.deleteParkingManager(auth.getName(), adminId, managerId);
    }

    @GetMapping("/api/admins/approved")
    public List<AdminDTO> getApprovedAdmins(Authentication auth) throws UserException {
        return adminService.getApprovedAdmins(auth.getName());
    }

    @GetMapping("/api/admins/not-approved")
    public List<AdminDTO> getNotApprovedAdmins(Authentication auth) throws UserException {
        return adminService.getNotApprovedAdmins(auth.getName());
    }

    @PostMapping("/api/admins/{admin-id}/add/{email}")
    public void addAdmin(Authentication auth,
                         @PathVariable("admin-id") Long adminId,
                         @PathVariable("email") String email)
            throws UserException, SecurityException, AuthException {
        adminService.addAdmin(auth.getName(), adminId, email);
    }

    @PostMapping("/api/admins/{admin-id}/approve/{new-admin-id}")
    public String approveAdmin(Authentication auth,
                               @PathVariable("admin-id") Long adminId,
                               @PathVariable("new-admin-id") Long newAdminId) throws UserException {
        return adminService.approveAdmin(auth.getName(), adminId, newAdminId);
    }

    @PostMapping("/api/admins/{admin-id}/decline/{new-admin-id}")
    public void declineAdmin(Authentication auth,
                             @PathVariable("admin-id") Long adminId,
                             @PathVariable("new-admin-id") Long newAdminId) throws UserException {
        adminService.declineAdmin(auth.getName(), adminId, newAdminId);
    }

    @PatchMapping("/api/admins/{admin-id}")
    public void updateAdmin(Authentication auth,
                            @PathVariable("admin-id") Long adminId,
                            @Valid @RequestBody UpdateAdminRequest updatedAdmin) throws UserException {
        adminService.updateAdmin(auth.getName(), adminId, updatedAdmin);
    }

    @GetMapping("/api/admins/{admin-id}/db/history")
    public List<DatabaseHistoryDTO> getWeekDatabaseHistory(Authentication auth,
                                                           @PathVariable("admin-id") Long adminId)
            throws UserException {
        return adminService.getWeekDatabaseHistory(auth.getName(), adminId);
    }

    @GetMapping(value = "/api/admins/{admin-id}/db/export", produces = "application/zip")
    public void exportDatabase(Authentication auth,
                               @PathVariable("admin-id") Long adminId,
                               HttpServletResponse response)
            throws SQLException, IOException, ClassNotFoundException, UserException {
        adminService.exportDatabase(auth.getName(), adminId, response);
    }

    @PostMapping("/api/admins/{admin-id}/db/import")
    public void importDatabase(Authentication auth,
                               @PathVariable("admin-id") Long adminId,
                               @RequestParam("file") MultipartFile file)
            throws SQLException, IOException, ClassNotFoundException, UserException, DatabaseException {
        adminService.importDatabase(auth.getName(), adminId, file);
    }
}
