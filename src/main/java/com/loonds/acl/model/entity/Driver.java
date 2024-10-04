package com.loonds.acl.model.entity;

import com.loonds.acl.model.enums.DriverStatus;
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
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    private String name;
    private String email;
    private String mobile;
    private String mcNumber;
    private String dotNumber;
    private String companyName;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;
    @ManyToOne
    User approvedBy;

    @CreatedDate
    Instant createdDate;
    @LastModifiedDate
    Instant modifiedDate;
}
