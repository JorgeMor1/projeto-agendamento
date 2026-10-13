package io.github.JorgeMor1.auth;

import java.time.Instant;

public class TokenResult {
    private final String accessToken;
    private final Instant expiresAt;
    //private final String refreshToken;

    public TokenResult(String accessToken, Instant expiresAt) {
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;

    }

    public String getAccessToken() {
        return accessToken;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }


}
