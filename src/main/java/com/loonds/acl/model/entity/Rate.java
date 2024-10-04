package com.loonds.acl.model.entity;

import com.loonds.acl.model.enums.RateType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    Channel channel;

    String label;
    Double amount;

    @Enumerated(EnumType.STRING)
    RateType type;

}
