package com.loonds.acl.model.dto;

import com.loonds.acl.model.entity.User;
import com.loonds.acl.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.time.Instant;

@Getter
@Setter
public class UserDto {
    String id;
    String firstName;
    String lastName;
    String email;
    String mobile;
    String personalEmail;

    String password;
    String alternativeMobile;
    byte[] file;
    Role role;
    Instant createdDate;
    Instant modifiedDate;

    public static UserDto of(User user){
        UserDto output = new UserDto();
        output.setId(user.getId());
        output.setFirstName(user.getFirstName());
        output.setLastName(user.getLastName());
        output.setEmail(user.getEmail());
        output.setMobile(user.getMobile());
        output.setRole(user.getRole());
        output.setFile(user.getProfilePic());
        output.setCreatedDate(user.getCreatedDate());
        output.setModifiedDate(user.getModifiedDate());
        return output;
    }
}
