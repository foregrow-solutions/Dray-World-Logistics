package com.loonds.acl.service;

import com.loonds.acl.model.dto.UserDto;
import com.loonds.acl.model.entity.User;
import com.loonds.acl.model.enums.Role;
import com.loonds.acl.model.payload.CreateEmployeePayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    void createAdmin();

    UserDto create (CreateEmployeePayload payload);

    Optional<UserDto> update(String id, UserDto userDto);

    Optional<UserDto> updateRole(String id, Role role);

    Optional<UserDto> get(String id);

    Page<UserDto> getAll(Pageable pageable);

    boolean validateCredentials(String rawPassword, String encodedPassword);

    Optional<User> findByUsername(String username);

    UserDto getMyProfile(String userId);

    void delete(String userId);

    boolean checkIfUserExists(String username);

//    void changeUserPassword(String userId, ChangePasswordPayload payload);

    void forgotUserPassword(String username);

    String checkPasswordRestOtp(String otp);
}
