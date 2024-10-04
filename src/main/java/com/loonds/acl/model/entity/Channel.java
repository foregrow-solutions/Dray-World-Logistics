package com.loonds.acl.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.loonds.acl.model.enums.ChannelType;
import com.loonds.acl.model.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Channel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String publicId;
    private String carrierNumber;
    private String weight;
    private String size;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    Customer customer;
    @ManyToOne
    Driver driver;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Rate> rates = new HashSet<>();

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Location> locations = new HashSet<>();

    @Enumerated
    ChannelType type;

    @Enumerated(EnumType.STRING)
    Status status;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Document> documents = new HashSet<>();

    @CreatedDate
    Instant createdDate;
    @LastModifiedDate
    Instant modifiedDate;
}
