package com.loonds.acl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final TokenService tokenService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //TODO: Any additional checks on username and password mapping
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication token provided");
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtToken = jwtAuthenticationToken.getRawToken();
        try {
            long t1 = System.currentTimeMillis();
            JwtUser rawUser = tokenService.validate(jwtToken);
            long t2 = System.currentTimeMillis();
            log.debug("Time to decode token: {}ms", (t2 - t1));
            //check if clientId is still valid and load the permissions associated with this client, throw access denied exception if client is deleted
            //throw new AccessDeniedException(String.format("Inactive access (AccessId=%s)", accessId));
            List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", List.of("")));
            return new AuthenticatedUser(rawUser.getUserId(), jwtToken, authorityList);
        } catch (Exception e) {
            log.error("Exception in decoding jwtToken", e);
            throw new BadCredentialsException(String.format("Authentication failure for Token=%s", jwtToken));
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
