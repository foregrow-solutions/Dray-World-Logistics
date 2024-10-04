package com.loonds.acl.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String rawToken;
    private String clientId;

    public JwtAuthenticationToken(String unsafeToken, String clientId) {
        super(null, null);
        this.rawToken = unsafeToken;
        this.clientId = clientId;
    }

    public String getRawToken() {
        return rawToken;
    }

    public String getClientId() {
        return clientId;
    }

    @Override
    public Object getCredentials() {
        return this.rawToken;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawToken = null;
        this.clientId = null;
    }
}
