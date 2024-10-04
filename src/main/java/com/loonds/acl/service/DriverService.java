package com.loonds.acl.service;

import com.loonds.acl.model.dto.DriverDto;
import com.loonds.acl.model.enums.DriverStatus;
import com.loonds.acl.model.payload.CreateDriverPayload;

import java.util.List;
import java.util.Optional;

public interface DriverService {

    DriverDto addDriver(String userId, CreateDriverPayload payload);

    Optional<DriverDto> updateDriver(String userId, long id, DriverDto driverDto);

    Optional<DriverDto> get(long id);

    Optional<DriverDto> isApproved(String userId, long id, DriverStatus status);

    List<DriverDto> getAllDriver(DriverStatus status);

}
