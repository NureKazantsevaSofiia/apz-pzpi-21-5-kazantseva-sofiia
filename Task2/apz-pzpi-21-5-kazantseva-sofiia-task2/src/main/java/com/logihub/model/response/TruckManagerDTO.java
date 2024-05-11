package com.logihub.model.response;

import com.logihub.model.entity.TruckManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TruckManagerDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String avatar;

    private CompanyDTO company;

    public TruckManagerDTO(TruckManager truckManager) {
        this.id = truckManager.getId();
        this.firstName = truckManager.getFirstName();
        this.lastName = truckManager.getLastName();
        this.email = truckManager.getEmail();
        this.avatar = truckManager.getAvatar();
        this.company = new CompanyDTO(truckManager.getCompany());
    }
}
