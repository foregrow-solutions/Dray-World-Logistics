package com.loonds.acl.model.dto;

import com.loonds.acl.model.entity.Query;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnquiryDto {
    String id;
    String fullName;
    String email;
    String phone;
    String message;

    public static EnquiryDto of(Query query){
        EnquiryDto output = new EnquiryDto();
        output.setId(query.getId());
        output.setFullName(query.getFullName());
        output.setEmail(query.getEmail());
        output.setPhone(query.getPhone());
        output.setMessage(query.getMessage());
        return output;
    }
}
