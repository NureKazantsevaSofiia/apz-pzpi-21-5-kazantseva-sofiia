package com.logihub.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

public class DatabaseException extends Exception {

    private final DatabaseExceptionProfile databaseExceptionProfile;

    public DatabaseException(DatabaseExceptionProfile databaseExceptionProfile) {
        super(databaseExceptionProfile.exceptionMessage);
        this.databaseExceptionProfile = databaseExceptionProfile;
    }

    public String getName() {
        return databaseExceptionProfile.exceptionName;
    }

    public HttpStatus getResponseStatus() {
        return databaseExceptionProfile.responseStatus;
    }

    @AllArgsConstructor
    public enum DatabaseExceptionProfile {

        INVALID_FILE("invalid_file",
                "File is invalid.", HttpStatus.BAD_REQUEST),

        INVALID_FILE_EXTENSION("invalid_file_extension",
                "File extension must be \".sql\".", HttpStatus.BAD_REQUEST),

        DATABASE_EXCEPTION("database_exception",
                "Something went wrong during importing data.", HttpStatus.BAD_REQUEST);

        private final String exceptionName;
        private final String exceptionMessage;
        private final HttpStatus responseStatus;
    }
}
