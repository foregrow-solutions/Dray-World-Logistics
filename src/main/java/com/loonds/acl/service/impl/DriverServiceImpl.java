package com.loonds.acl.service.impl;

import com.loonds.acl.model.dto.DriverDto;
import com.loonds.acl.model.entity.Driver;
import com.loonds.acl.model.enums.DriverStatus;
import com.loonds.acl.model.payload.CreateDriverPayload;
import com.loonds.acl.repository.DriverRepository;
import com.loonds.acl.repository.UserRepository;
import com.loonds.acl.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public DriverDto addDriver(String userId, CreateDriverPayload payload) {
        Driver driver = new Driver();
        driver.setName(payload.driverName());
        driver.setEmail(payload.driverEmail());
        driver.setMobile(payload.driverMobile());
        driver.setMcNumber(payload.mcNumber());
        driver.setDotNumber(payload.dotNumber());
        driver.setCompanyName(payload.companyName());
        driver.setUser(userRepository.getReferenceById(userId));
        driver.setStatus(DriverStatus.APPROVED);
        Driver save = driverRepository.save(driver);
        return DriverDto.of(save);
    }

    @Override
    @Transactional
    public Optional<DriverDto> updateDriver(String userId, long id, DriverDto driverDto) {
        return driverRepository.findById(id).map(driver -> {
            driver.setName(driverDto.getDriverName());
            driver.setEmail(driverDto.getDriverEmail());
            driver.setMobile(driverDto.getDriverMobile());
            driver.setMcNumber(driverDto.getMcNumber());
            driver.setDotNumber(driverDto.getDotNumber());
            driver.setStatus(DriverStatus.APPROVED);
            driver.setCompanyName(driverDto.getCompanyName());
            return DriverDto.of(driver);
        });
    }

    @Override
    public Optional<DriverDto> get(long id) {
        return driverRepository.findById(id).map(DriverDto::of);
    }

    @Override
    @Transactional
    public Optional<DriverDto> isApproved(String userId, long id, DriverStatus status) {
        return driverRepository.findById(id).map(driver -> {
            driver.setStatus(status);
            driver.setApprovedBy(userRepository.getReferenceById(userId));
            return DriverDto.of(driver);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverDto> getAllDriver(DriverStatus status) {
        return driverRepository.findAllByStatus(status).stream().map(DriverDto::of).collect(Collectors.toList());
    }
}
