package com.quadra.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtUtils {
    private final String accessSecret;
    private final String refreshSecret;
    private final int accessExpiresInSeconds;
    private final int refreshExpiresInSeconds;

    public JwtUtils(JwtProperties jwtProperties) {
        this(jwtProperties.getAccess().getSecret(),
                jwtProperties.getRefresh().getSecret(),
                jwtProperties.getAccess().getExpires(),
                jwtProperties.getRefresh().getExpires()
        );
    }

    public JwtUtils(String accessSecret, String refreshSecret, int accessExpiresInSeconds, int refreshExpiresInSeconds) {
        this.accessSecret = accessSecret;
        this.refreshSecret = refreshSecret;
        this.accessExpiresInSeconds = accessExpiresInSeconds;
        this.refreshExpiresInSeconds = refreshExpiresInSeconds;
    }

    public String createAccessToken(String userId, String role, String provider, long currentTimeMillis) {
        return JWT.create()
                .withSubject(userId)
                .withClaim("role", role)
                .withClaim("provider", provider)
                .withIssuedAt(new Date(currentTimeMillis))
                .withExpiresAt(new Date(currentTimeMillis + this.accessExpiresInSeconds * 1000L))
                .sign(Algorithm.HMAC256(this.accessSecret));
    }

    public String createRefreshToken(String userId, long currentTimeMillis) {
        return JWT.create()
                .withSubject(userId)
                .withIssuedAt(new Date(currentTimeMillis))
                .withExpiresAt(new Date(currentTimeMillis + this.refreshExpiresInSeconds * 1000L))
                .sign(Algorithm.HMAC256(this.refreshSecret));
    }

    public boolean verifyAccessToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(this.accessSecret)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyRefreshToken(String token) {
        // even if it verifies a token, it doesn't mean that the token is valid.
        // you must compare it with redis
        try {
            JWT.require(Algorithm.HMAC256(this.refreshSecret)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
