package com.loonds.acl.service;

import com.loonds.acl.model.dto.CustomerDto;
import com.loonds.acl.model.entity.User;
import com.loonds.acl.model.enums.CustomerStatus;
import com.loonds.acl.model.payload.CreateCustomerPayload;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerDto add(String userId, CreateCustomerPayload payload);
    Optional<CustomerDto> update(String userId, long id, CustomerDto customerDto);
    Optional<CustomerDto> get(long id);
    Optional<CustomerDto> isApproved(String userId, long id, CustomerStatus status);
    List<CustomerDto> getAllCustomer(String userId);
    List<CustomerDto> getAll();
    List<CustomerDto> getAllUnApproved();
    List<CustomerDto> getAllUnApprovedCustomer(boolean isApproved);
}
