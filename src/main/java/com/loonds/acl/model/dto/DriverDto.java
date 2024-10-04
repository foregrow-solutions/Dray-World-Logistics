package com.loonds.acl.model.dto;

import com.loonds.acl.model.entity.Driver;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDto {
    long id;
    String driverName;
    String driverEmail;
    String driverMobile;
    String mcNumber;
    String dotNumber;
    String companyName;
    boolean isApproved;

    public static DriverDto of(Driver driver) {
        DriverDto output = new DriverDto();
        output.setId(driver.getId());
        output.setCompanyName(driver.getCompanyName());
        output.setDriverName(driver.getName());
        output.setDriverEmail(driver.getEmail());
        output.setDriverMobile(driver.getMobile());
        output.setMcNumber(driver.getMcNumber());
        output.setDotNumber(driver.getDotNumber());
        return output;
    }
}
