package io.github.JorgeMor1.auth.dto.response;

import io.github.JorgeMor1.usuarios.enums.UserRole;
import io.github.JorgeMor1.usuarios.enums.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record UserSummaryDTO(
        UUID id,
        String firstName,
        String lastName,
        UserRole role,
        UserStatus userStatus,
        Instant createdAt
) {}
