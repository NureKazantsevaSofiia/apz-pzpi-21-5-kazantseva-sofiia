package com.logihub.repository;

import com.logihub.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Boolean existsByEmailIgnoreCase(String email);

    Optional<Admin> findByEmailIgnoreCase(String email);

    List<Admin> findAllByApprovedFalseOrderByDateOfAdding();

    List<Admin> findAllByApprovedTrueOrderByDateOfApproving();
}