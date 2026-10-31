package io.github.JorgeMor1.auth;

import java.time.Instant;

public record TokenResult (
    String accessToken,
    Instant expiresAt
) {}
