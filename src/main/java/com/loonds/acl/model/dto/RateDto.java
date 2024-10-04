package com.loonds.acl.model.dto;

import com.loonds.acl.model.entity.Rate;
import com.loonds.acl.model.enums.RateType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateDto {
    long id;
    String label;
    Double amount;
    RateType type;

    public static RateDto of(Rate rate) {
        RateDto output = new RateDto();
        output.setId(rate.getId());
        output.setLabel(rate.getLabel());
        output.setAmount(rate.getAmount());
        output.setType(rate.getType());
        return output;
    }
}
