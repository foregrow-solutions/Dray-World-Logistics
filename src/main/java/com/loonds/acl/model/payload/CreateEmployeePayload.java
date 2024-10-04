package com.loonds.acl.model.payload;

import com.loonds.acl.common.validator.UniqueUsername;
import com.loonds.acl.model.enums.Role;

import java.time.LocalDate;

public record CreateEmployeePayload(String firstName, String lastName, String personalEmail, @UniqueUsername String email, String mobile, String alternativeMobile, LocalDate dob, Role role, LocalDate doj) {
}
