package com.logihub.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

public class UserException extends Exception {

    private final UserExceptionProfile userExceptionProfile;

    public UserException(UserExceptionProfile userExceptionProfile) {
        super(userExceptionProfile.exceptionMessage);
        this.userExceptionProfile = userExceptionProfile;
    }

    public String getName() {
        return userExceptionProfile.exceptionName;
    }

    public HttpStatus getResponseStatus() {
        return userExceptionProfile.responseStatus;
    }

    @AllArgsConstructor
    public enum UserExceptionProfile {

        USER_NOT_FOUND("user_not_found",
                "User is not found.", HttpStatus.NOT_FOUND),

        ADMIN_NOT_FOUND("admin_not_found",
                "Admin is not found.", HttpStatus.NOT_FOUND),

        TRUCK_MANAGER_NOT_FOUND("truck_manager_not_found",
                "Truck manager is not found.", HttpStatus.NOT_FOUND),

        PARKING_MANAGER_NOT_FOUND("parking_manager_not_found",
                "Parking manager is not found.", HttpStatus.NOT_FOUND),

        NOT_ADMIN("not_admin",
                "You are not admin.", HttpStatus.FORBIDDEN),

        NOT_CHIEF_ADMIN("not_chief_admin",
                "You are not chief admin.", HttpStatus.FORBIDDEN),

        NOT_MANAGER("not_manager",
                "You are not manager.", HttpStatus.FORBIDDEN),

        NOT_TRUCK_MANAGER("not_truck_manager",
                "You are not truck manager.", HttpStatus.FORBIDDEN),

        NOT_PARKING_MANAGER("not_parking_manager",
                "You are not parking manager.", HttpStatus.FORBIDDEN),

        EMAIL_MISMATCH("email_mismatch",
                "Email provided does not match the user's email.", HttpStatus.FORBIDDEN),

        SOMETHING_WRONG("something_wrong",
                "Something went wrong.", HttpStatus.BAD_REQUEST),

        PERMISSION_DENIED("permission_denied",
                "Permission denied.", HttpStatus.FORBIDDEN),

        FORBIDDEN("forbidden",
                "Forbidden.", HttpStatus.BAD_REQUEST);


        private final String exceptionName;
        private final String exceptionMessage;
        private final HttpStatus responseStatus;
    }
}