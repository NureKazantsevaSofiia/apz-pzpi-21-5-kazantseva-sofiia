package com.logihub.model.response;

import com.logihub.model.entity.DatabaseHistory;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DatabaseHistoryDTO {

    private Long id;

    private String adminEmail;

    private LocalDateTime time;

    private String operation;

    public DatabaseHistoryDTO(DatabaseHistory history) {
        this.id = history.getId();
        this.adminEmail = history.getAdminEmail();
        this.time = history.getTime();
        this.operation = history.getOperation().name();
    }
}
