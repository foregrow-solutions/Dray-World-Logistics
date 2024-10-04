package com.loonds.acl.model.payload;

import lombok.Data;

@Data
public class CreateQueryPayload {
    String id;
    String fullName;
    String email;
    String phone;
    String message;
}
