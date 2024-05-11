package com.logihub.service;

import com.logihub.exception.AuthException;
import com.logihub.exception.DatabaseException;
import com.logihub.exception.UserException;
import com.logihub.model.request.UpdateAdminRequest;
import com.logihub.model.response.AdminDTO;
import com.logihub.model.response.DatabaseHistoryDTO;
import com.logihub.model.response.ParkingManagerDTO;
import com.logihub.model.response.TruckManagerDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.mail.MailException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface AdminService {

    AdminDTO getAdmin(String email, Long adminId) throws UserException;

    void addAdmin(String email, Long adminId, String newAdminEmail)
            throws UserException, SecurityException, MailException, AuthException;

    List<AdminDTO> getApprovedAdmins(String email) throws UserException;

    List<AdminDTO> getNotApprovedAdmins(String email) throws UserException;

    String approveAdmin(String email, Long adminId, Long newAdminId) throws UserException, MailException;

    void declineAdmin(String email, Long adminId, Long newAdminId) throws UserException, MailException;

    void updateAdmin(String email, Long adminId, UpdateAdminRequest updatedAdmin) throws UserException;

    void deleteTruckManager(String email, Long adminId, Long truckManagerId) throws UserException, SecurityException;

    void deleteParkingManager(String email, Long adminId, Long parkingManagerId) throws UserException, SecurityException;

    List<TruckManagerDTO> getTruckManagers(String email, Long adminId) throws UserException;

    List<ParkingManagerDTO> getParkingManagers(String email, Long adminId) throws UserException;

    List<DatabaseHistoryDTO> getWeekDatabaseHistory(String email, Long adminId) throws UserException;

    void exportDatabase(String email, Long adminId, HttpServletResponse response)
            throws SQLException, IOException, ClassNotFoundException, UserException;

    void importDatabase(String email, Long adminId, MultipartFile file)
            throws SQLException, ClassNotFoundException, IOException, UserException, DatabaseException;
}
