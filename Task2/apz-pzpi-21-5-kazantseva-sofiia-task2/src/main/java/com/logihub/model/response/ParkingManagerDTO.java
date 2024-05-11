package com.logihub.model.response;

import com.logihub.model.entity.ParkingManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingManagerDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String avatar;

    private CompanyDTO company;

    public ParkingManagerDTO(ParkingManager parkingManager) {
        this.id = parkingManager.getId();
        this.firstName = parkingManager.getFirstName();
        this.lastName = parkingManager.getLastName();
        this.email = parkingManager.getEmail();
        this.avatar = parkingManager.getAvatar();
        this.company = new CompanyDTO(parkingManager.getCompany());
    }
}
