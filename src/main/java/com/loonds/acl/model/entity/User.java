package com.loonds.acl.model.entity;

import com.loonds.acl.model.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String pid;
    private String firstName;
    private String lastName;
    private String personalEmail;
    private String email;
    private String mobile;
    private String password;
    private Date dob;
    private Date doj;

    @Enumerated(EnumType.STRING)
    Role role;

    @Lob
    @Column(name = "profilePic", length = 1000)
    private byte[] profilePic;


    @CreatedDate
    Instant createdDate;
    @LastModifiedDate
    Instant modifiedDate;

}
