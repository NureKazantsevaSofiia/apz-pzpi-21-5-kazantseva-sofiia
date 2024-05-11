package com.logihub.model.entity;

import com.logihub.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@Table(name = "admins")
public class Admin extends User {

    @Column(name = "date_of_approving")
    private LocalDateTime dateOfApproving;

    @Column(name = "date_of_adding")
    private LocalDateTime dateOfAdding;

    @Column(name = "added_by")
    private String addedBy;

    @Column(name = "is_approved")
    private boolean approved;

    @Column(name = "is_new_account")
    private boolean newAccount;

    public Admin() {
        this.setRole(Role.ADMIN);
        this.setApproved(false);
        this.setNewAccount(true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Admin admin = (Admin) o;
        return approved == admin.approved && newAccount == admin.newAccount &&
                Objects.equals(dateOfApproving, admin.dateOfApproving) &&
                Objects.equals(dateOfAdding, admin.dateOfAdding) && Objects.equals(addedBy, admin.addedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateOfApproving, dateOfAdding, addedBy, approved, newAccount);
    }
}