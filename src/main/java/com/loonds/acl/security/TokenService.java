package com.loonds.acl.security;

import com.loonds.acl.common.ApplicationException;
import com.loonds.acl.common.ErrorCode;
import com.loonds.acl.config.JwtSettings;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TokenService {

    private static final long EXPIRATION_TIME = 900_000;
    private static final long OTP_EXPIRY = 300_000;

    private final JwtSettings jwtSettings;
    private final Key key;

    public TokenService(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
        this.key = Keys.hmacShaKeyFor(this.jwtSettings.getTokenSigningKey().getBytes(StandardCharsets.UTF_8));
    }

    public String generate(String userId, long expiryInDays) {
        String jws = Jwts.builder()
                .setId(UUID.randomUUID().toString()) //This should be used as an id for a given token, can be used to invalidate the token or revoke the token
                .signWith(key, SignatureAlgorithm.HS512)
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(expiryInDays)))
//                .setIssuer(guiServerSecurityProperties.getTokenIssuer())
//                .setAudience(guiServerSecurityProperties.getTokenAudience())
                .compact();
        return jws;
    }

    public String generateTokenPartner(String userId,String orgId, String hash, long expiryInDays) {
        String jws = Jwts.builder()
                .setId(UUID.randomUUID().toString()) //This should be used as an id for a given token, can be used to invalidate the token or revoke the token
                .signWith(key, SignatureAlgorithm.HS512)
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(expiryInDays)))
                .claim("hash", hash)
                .claim("orgId", orgId)
//                .setIssuer(guiServerSecurityProperties.getTokenIssuer())
//                .setAudience(guiServerSecurityProperties.getTokenAudience())
                .compact();
        return jws;
    }

    public JwtUser validate(String compactJws) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).setAllowedClockSkewSeconds(30).build().parseClaimsJws(compactJws);
            return new JwtUser(claims.getBody().getSubject());
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            log.warn("Invalid JWT Token", e);
            throw new BadCredentialsException("Invalid JWT token: " + compactJws, e);
        } catch (ExpiredJwtException e) {
            log.info("JWT Token is expired", e);
            throw new ApplicationException(ErrorCode.JWT_TOKEN_EXPIRED, "Jwt token expired", e);
        }
    }  public JwtUserHash validateTokenPartner(String compactJws) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).setAllowedClockSkewSeconds(30).build().parseClaimsJws(compactJws);
            return new JwtUserHash(claims.getBody().getSubject(), claims.getBody().get("hash", String.class));
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            log.warn("Invalid JWT Token", e);
            throw new BadCredentialsException("Invalid JWT token: " + compactJws, e);
        } catch (ExpiredJwtException e) {
            log.info("JWT Token is expired", e);
            throw new ApplicationException(ErrorCode.JWT_TOKEN_EXPIRED, "Jwt token expired", e);
        }
    }

    public String createShareToken(String vaccinationId, Duration expiryDuration) {
        String jws = Jwts.builder()
                .setId(UUID.randomUUID().toString()) //This should be used as an id for a given token, can be used to invalidate the token or revoke the token
                .signWith(key, SignatureAlgorithm.HS512)
                .setSubject("Acl Service Record")
                .setExpiration(new Date(System.currentTimeMillis() + expiryDuration.toMillis()))
                .claim("vid", vaccinationId)
                .setIssuer(jwtSettings.getTokenIssuer())
//                .setAudience(guiServerSecurityProperties.getTokenAudience())
                .compact();
        log.info("Sharing token generated: {}", jws);
        return jws;
    }

    public String decodeShareToken(String compactJws) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).setAllowedClockSkewSeconds(30).build().parseClaimsJws(compactJws);
        return claims.getBody().get("vid", String.class);
    }
}
