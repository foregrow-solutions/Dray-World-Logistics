package com.loonds.acl.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String CLIENT_HEADER_NAME = "client-id";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final static UrlPathHelper urlPathHelper = new UrlPathHelper();

    public JwtTokenAuthenticationProcessingFilter() {
        super(request -> {
            String header = request.getHeader(AUTH_HEADER_NAME);
            return header != null && StringUtils.startsWithIgnoreCase(header, TOKEN_PREFIX);
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String tokenPayload = request.getHeader(AUTH_HEADER_NAME);
        String clientId = request.getHeader(CLIENT_HEADER_NAME);
        if (tokenPayload != null && StringUtils.startsWithIgnoreCase(tokenPayload, TOKEN_PREFIX)) {
            String authToken = tokenPayload.substring(TOKEN_PREFIX.length());
            JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken, clientId);
            return getAuthenticationManager().authenticate(authRequest);
        } else {
            throw new AuthenticationServiceException("Authentication headers can not be null: " + request.getMethod());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all

        response.addHeader("Access-Control-Allow-Origin", "*");
        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            // CORS "pre-flight" request
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            // response.addHeader("Access-Control-Allow-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Max-Age", "1");
        }
        chain.doFilter(request, response);
    }
}
