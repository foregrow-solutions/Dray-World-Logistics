package com.loonds.acl.model.payload;

public record CreateCustomerPayload(String customerName,String companyName,String companyAddress, String customerEmail, String customerMobile) {
}
