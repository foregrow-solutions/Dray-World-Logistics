package com.loonds.acl.model.payload;

import lombok.Data;

@Data
public class UserLoginPayload {
    String username;
    String password;
}
