package com.loonds.acl.repository;

import com.loonds.acl.model.entity.Driver;
import com.loonds.acl.model.entity.User;
import com.loonds.acl.model.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findAllByStatus(DriverStatus status);
}
