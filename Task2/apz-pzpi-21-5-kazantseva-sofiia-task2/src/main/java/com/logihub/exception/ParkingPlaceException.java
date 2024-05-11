package com.logihub.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

public class ParkingPlaceException extends Exception {

    private final ParkingPlaceExceptionProfile parkingPlaceExceptionProfile;

    public ParkingPlaceException(ParkingPlaceExceptionProfile parkingPlaceExceptionProfile) {
        super(parkingPlaceExceptionProfile.exceptionMessage);
        this.parkingPlaceExceptionProfile = parkingPlaceExceptionProfile;
    }

    public String getName() {
        return parkingPlaceExceptionProfile.exceptionName;
    }

    public HttpStatus getResponseStatus() {
        return parkingPlaceExceptionProfile.responseStatus;
    }

    @AllArgsConstructor
    public enum ParkingPlaceExceptionProfile {

        PARKING_PLACE_NOT_FOUND("parking_place_not_found",
                "Parking place with such id is not found.", HttpStatus.BAD_REQUEST),

        FORBIDDEN("forbidden",
                "You cannot change the parking place information because you are not its manager.",
                HttpStatus.FORBIDDEN),

        PARKING_PLACE_HAS_MANAGER("parking_place_has_manager",
                "Parking place has already had manager.",
                HttpStatus.FORBIDDEN);

        private final String exceptionName;
        private final String exceptionMessage;
        private final HttpStatus responseStatus;
    }
}
