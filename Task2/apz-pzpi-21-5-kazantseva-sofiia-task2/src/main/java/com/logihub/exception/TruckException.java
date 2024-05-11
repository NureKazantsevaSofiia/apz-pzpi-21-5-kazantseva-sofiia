package com.logihub.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

public class TruckException extends Exception {

    private final TruckExceptionProfile truckExceptionProfile;

    public TruckException(TruckExceptionProfile truckExceptionProfile) {
        super(truckExceptionProfile.exceptionMessage);
        this.truckExceptionProfile = truckExceptionProfile;
    }

    public String getName() {
        return truckExceptionProfile.exceptionName;
    }

    public HttpStatus getResponseStatus() {
        return truckExceptionProfile.responseStatus;
    }

    @AllArgsConstructor
    public enum TruckExceptionProfile {

        TRUCK_NOT_FOUND("truck_not_found",
                "Truck with such id is not found.", HttpStatus.BAD_REQUEST),

        FORBIDDEN("forbidden",
                "You cannot change the truck information because you are not its manager.",
                HttpStatus.FORBIDDEN),

        TRUCK_HAS_MANAGER("truck_has_manager",
                "Truck has already had manager.",
                HttpStatus.FORBIDDEN);

        private final String exceptionName;
        private final String exceptionMessage;
        private final HttpStatus responseStatus;
    }
}
