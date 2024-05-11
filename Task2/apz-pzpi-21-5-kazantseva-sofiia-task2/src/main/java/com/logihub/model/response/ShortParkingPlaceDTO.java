package com.logihub.model.response;

import com.logihub.model.entity.ParkingPlace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShortParkingPlaceDTO {

    private Long id;

    private String address;

    private Double hourlyPay;

    private String parkingManagerEmail;

    public ShortParkingPlaceDTO(ParkingPlace parkingPlace) {
        this.id = parkingPlace.getId();
        this.address = parkingPlace.getAddress();
        this.hourlyPay = parkingPlace.getHourlyPay();
        this.parkingManagerEmail = parkingPlace.getParkingManager().getEmail();
    }
}
