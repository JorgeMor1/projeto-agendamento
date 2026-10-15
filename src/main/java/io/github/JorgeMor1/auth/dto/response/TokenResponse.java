package io.github.JorgeMor1.auth.dto.response;

import java.time.Instant;

public record TokenResponse(
        String accessToken,
        Instant expiresAt,
        String tokenType
) {
}
