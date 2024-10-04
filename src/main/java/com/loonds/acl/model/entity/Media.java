package com.loonds.acl.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
public class Media {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    String id;

    private String publicId;
    private String fileName;
    private String fileType;
    private String filePath;
    @Column(length = 25)
    private String size;
    private String url;

    @ManyToOne
    User createdBy;

    @CreatedDate
    private Instant createdDate;
}
