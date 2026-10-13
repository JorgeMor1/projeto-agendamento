package io.github.JorgeMor1.auth.dto;

import io.github.JorgeMor1.usuarios.enums.UserRole;
import io.github.JorgeMor1.usuarios.enums.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record RegisterResponse(
       UUID id,
       String firstName,
       String lastName,
       UserRole role,
       UserStatus userStatus,
       Instant createdAt,
       String accessToken,
       Instant expiresAt,
       String tokenType
) {}
