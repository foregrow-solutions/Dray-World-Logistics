package com.loonds.acl.repository;

import com.loonds.acl.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);
}
