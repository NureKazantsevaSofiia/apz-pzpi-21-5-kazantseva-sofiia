package com.logihub.model.response;

import com.logihub.model.entity.Truck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShortTruckDTO {

    private Long id;

    private String number;

    private String truckManagerEmail;

    public ShortTruckDTO(Truck truck) {
        this.id = truck.getId();
        this.number = truck.getNumber();
        String email = "-";
        if (truck.getTruckManager() != null) {
            email = truck.getTruckManager().getEmail();
        }
        this.truckManagerEmail = email;
    }
}
