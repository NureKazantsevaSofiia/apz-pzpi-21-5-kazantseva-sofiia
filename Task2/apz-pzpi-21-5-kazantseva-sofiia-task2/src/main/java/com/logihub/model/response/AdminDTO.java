package com.logihub.model.response;

import com.logihub.model.entity.Admin;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String avatar;

    private LocalDateTime dateOfApproving;

    private LocalDateTime dateOfAdding;

    private String addedBy;

    private boolean approved;

    private boolean newAccount;

    public AdminDTO(Admin admin) {
        this.id = admin.getId();
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
        this.email = admin.getEmail();
        this.avatar = admin.getAvatar();
        this.dateOfApproving = admin.getDateOfApproving();
        this.dateOfAdding = admin.getDateOfAdding();
        this.addedBy = admin.getAddedBy();
        this.approved = admin.isApproved();
        this.newAccount = admin.isNewAccount();
    }
}
