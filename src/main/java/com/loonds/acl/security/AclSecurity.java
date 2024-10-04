package com.loonds.acl.security;

import com.loonds.acl.model.enums.Role;
import com.loonds.acl.model.entity.User;
import com.loonds.acl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class AclSecurity {
    private final UserRepository userRepo;

    @Transactional(readOnly = true)
    public boolean hasUserEditPermission(@CurrentUser AuthenticatedUser authenticatedUser){

        User user = userRepo.getReferenceById(authenticatedUser.getUsername());
        return user.getRole() == Role.ADMIN;
    }
}
