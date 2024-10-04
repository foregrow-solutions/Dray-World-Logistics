package com.loonds.acl.model.payload;

public record CreateDriverPayload(String driverName, String driverEmail, String driverMobile, String mcNumber, String dotNumber, String companyName) {
}
