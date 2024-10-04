package com.loonds.acl.service.impl;

import com.loonds.acl.model.dto.CustomerDto;
import com.loonds.acl.model.entity.Customer;
import com.loonds.acl.model.enums.CustomerStatus;
import com.loonds.acl.model.payload.CreateCustomerPayload;
import com.loonds.acl.repository.CustomerRepository;
import com.loonds.acl.repository.UserRepository;
import com.loonds.acl.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CustomerDto add(String userId, CreateCustomerPayload payload) {
        Customer customer = new Customer();
        customer.setName(payload.customerName());
        customer.setEmail(payload.customerEmail());
        customer.setMobile(payload.customerMobile());
        customer.setCompanyName(payload.companyName());
        customer.setCompanyAddress(payload.companyAddress());
        customer.setUser(userRepository.getReferenceById(userId));
        customer.setStatus(CustomerStatus.PENDING);
        customer.setCreatedDate(Instant.now());
        customer.setModifiedDate(Instant.now());
        Customer save = customerRepository.save(customer);
        return CustomerDto.of(save);
    }

    @Override
    @Transactional
    public Optional<CustomerDto> update(String userId, long id, CustomerDto customerDto) {
        return customerRepository.findById(id).map(customer -> {
            customer.setName(customerDto.getCustomerName());
            customer.setMobile(customerDto.getCustomerMobile());
            customer.setEmail(customerDto.getCustomerEmail());
            customer.setCompanyName(customerDto.getCompanyName());
            customer.setCompanyAddress(customerDto.getCompanyAddress());
            customer.setModifiedDate(Instant.now());
            return CustomerDto.of(customer);
        });
    }

    @Override
    public Optional<CustomerDto> get(long id) {
        return customerRepository.findById(id).map(CustomerDto::of);
    }

    @Override
    @Transactional
    public Optional<CustomerDto> isApproved(String userId, long id, CustomerStatus status) {
        return customerRepository.findById(id).map(customer -> {
            customer.setStatus(status);
            customer.setApprovedBy(userRepository.getReferenceById(userId));
            return CustomerDto.of(customer);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> getAllCustomer(String userId) {
        return customerRepository.findAllByUserAndStatus(userRepository.getReferenceById(userId), CustomerStatus.APPROVED).stream().map(CustomerDto::of).collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> getAll() {
        return customerRepository.findAll().stream().map(CustomerDto::of).toList();
    }

    @Override
    public List<CustomerDto> getAllUnApproved() {
        return customerRepository.findAllByStatus(CustomerStatus.PENDING).stream().map(CustomerDto::of).collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> getAllUnApprovedCustomer(boolean isApproved) {
        return null;
//        return customerRepository.findAllByIsApproved(isApproved).stream().map(CustomerDto::of).collect(Collectors.toList());
    }
}
