package com.logihub.model.response;

import com.logihub.model.entity.ParkingPlace;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingPlaceDTO {

    private Long id;

    private String placeNumber;

    private String address;

    private Double minWidth;

    private Double minHeight;

    private Double minLength;

    private Double minWeight;

    private Double maxWidth;

    private Double maxHeight;

    private Double maxLength;

    private Double maxWeight;

    private Double hourlyPay;

    public ParkingPlaceDTO(ParkingPlace parkingPlace) {
        this.id = parkingPlace.getId();
        this.address = parkingPlace.getAddress();
        this.placeNumber = parkingPlace.getPlaceNumber();
        this.minWidth = parkingPlace.getMinWidth();
        this.minHeight = parkingPlace.getMinHeight();
        this.minLength = parkingPlace.getMinLength();
        this.minWeight = parkingPlace.getMinWeight();
        this.maxWidth = parkingPlace.getMaxWidth();
        this.maxHeight = parkingPlace.getMaxHeight();
        this.maxLength = parkingPlace.getMaxLength();
        this.maxWeight = parkingPlace.getMaxWeight();
        this.hourlyPay = parkingPlace.getHourlyPay();
    }
}
