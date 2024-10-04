package com.loonds.acl.model.dto;

import com.loonds.acl.model.entity.Customer;
import com.loonds.acl.model.enums.CustomerStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Setter
public class CustomerDto {
    long id;
    String customerName;
    String customerEmail;
    String customerMobile;
    String companyName;
    String companyAddress;
    CustomerStatus status;
    Instant createdDate;

    public static CustomerDto of(Customer customer) {
        CustomerDto output = new CustomerDto();
        output.setId(customer.getId());
        output.setCustomerName(customer.getName());
        output.setCustomerEmail(customer.getEmail());
        output.setCustomerMobile(customer.getMobile());
        output.setCompanyName(customer.getCompanyName());
        output.setCompanyAddress(customer.getCompanyAddress());
        output.setStatus(customer.getStatus());
        output.setCreatedDate(customer.getCreatedDate());
        return output;
    }
}
