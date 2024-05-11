package com.logihub.repository;

import com.logihub.model.entity.TruckManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckManagerRepository extends JpaRepository<TruckManager, Long> {
}
