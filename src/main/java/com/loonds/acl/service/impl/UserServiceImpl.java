package com.loonds.acl.service.impl;

import com.loonds.acl.model.dto.UserDto;
import com.loonds.acl.model.entity.User;
import com.loonds.acl.model.enums.Role;
import com.loonds.acl.model.payload.CreateEmployeePayload;
import com.loonds.acl.repository.UserRepository;
import com.loonds.acl.service.UserService;
import com.loonds.acl.util.RandomIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createAdmin() {
        String adminEmail = "admin@admin.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            User adminUser = new User();
            adminUser.setFirstName("Pankaj");
            adminUser.setLastName("Kumar");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode("password"));
            adminUser.setRole(Role.ADMIN);
            userRepository.save(adminUser);
        }
    }

    @Override
    @Transactional
    public UserDto create(CreateEmployeePayload payload) {
        User user = new User();
        user.setPid("DWL-" + RandomIdGenerator.generateRandomId(4));
        user.setFirstName(payload.firstName());
        user.setLastName(payload.lastName());
        user.setPersonalEmail(payload.personalEmail());
        user.setEmail(payload.email());
        user.setMobile(payload.mobile());
//        var rewPassword = payload.firstName().substring(0, 2) + payload.dob().getDayOfWeek() + payload.dob().getMonthValue();
//        log.info("Raw Password : {}", rewPassword);
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole(payload.role());
        user.setCreatedDate(Instant.now());
        User save = userRepository.save(user);
        return UserDto.of(save);
    }

    @Override
    @Transactional
    public Optional<UserDto> update(String id, UserDto userDto) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setMobile(userDto.getMobile());
            user.setPersonalEmail(userDto.getPersonalEmail());
            user.setModifiedDate(Instant.now());
            return UserDto.of(user);
        });
    }

    @Override
    @Transactional
    public Optional<UserDto> updateRole(String id, Role role) {
        return userRepository.findById(id).map(user -> {
            user.setRole(role);
            userRepository.save(user);
            return UserDto.of(user);
        });
    }

    @Override
    public Optional<UserDto> get(String id) {
        return userRepository.findById(id).map(UserDto::of);
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDto::of);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateCredentials(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByEmail(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getMyProfile(String userId) {
        User user = userRepository.getReferenceById(userId);
        return UserDto.of(user);
    }

    @Override
    @Transactional
    public void delete(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }
    }

    @Override
    @Transactional
    public boolean checkIfUserExists(String username) {
        Optional<User> user = userRepository.findUserByEmail(username);
        return user.isEmpty();
    }

    @Override
    public void forgotUserPassword(String username) {

    }

    @Override
    public String checkPasswordRestOtp(String otp) {
        return null;
    }
}
