package com.loonds.acl.repository;

import com.loonds.acl.model.entity.Customer;
import com.loonds.acl.model.entity.User;
import com.loonds.acl.model.enums.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByUserAndStatus(User user, CustomerStatus status);

    List<Customer> findAllByStatus(CustomerStatus status);
}
