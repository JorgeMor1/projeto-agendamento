package io.github.JorgeMor1.auth.dto;

import java.util.Set;
import java.util.UUID;

public record LoginResponse (
    String accessToken,
    String tokenType,
    Long expiresIn,
    String refreshToken,
    UserSummaryDTO user)
        {}

record UserSummaryDTO(
        UUID id_user,
        String name,
        String login,
        Set<String>roles
)
{}