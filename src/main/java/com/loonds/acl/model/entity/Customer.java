package com.loonds.acl.model.entity;

import com.loonds.acl.model.enums.CustomerStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    private String name;
    private String companyName;
    private String companyAddress;
    private String email;
    private String mobile;
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
    @ManyToOne
    User approvedBy;

    @CreatedDate
    Instant createdDate;
    @LastModifiedDate
    Instant modifiedDate;
}
