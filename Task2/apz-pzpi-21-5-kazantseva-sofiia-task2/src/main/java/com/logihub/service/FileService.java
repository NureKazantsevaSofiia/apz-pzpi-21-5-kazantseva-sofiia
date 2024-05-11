package com.logihub.service;

import com.itextpdf.text.DocumentException;
import com.logihub.exception.UserException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public interface FileService {

    void generateWeekDatabaseReport(String email, Long adminId, HttpServletResponse response)
            throws IOException, DocumentException, UserException;

    void generateMonthDatabaseReport(String email, Long adminId, HttpServletResponse response)
            throws IOException, DocumentException, UserException;

    void generateDatabaseZipDump(HttpServletResponse response)
            throws SQLException, IOException, ClassNotFoundException;

    boolean importDatabase(String sql) throws SQLException, ClassNotFoundException;
}
