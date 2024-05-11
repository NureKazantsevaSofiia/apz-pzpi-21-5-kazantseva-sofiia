package com.logihub.service;

import com.logihub.model.entity.Admin;
import com.logihub.model.response.DatabaseHistoryDTO;

import java.util.List;

public interface DatabaseHistoryService {

    List<DatabaseHistoryDTO> getWeekDatabaseHistory();

    List<DatabaseHistoryDTO> getMonthDatabaseHistory();

    void saveExportOperation(Admin admin);

    void saveImportOperation(Admin admin);
}