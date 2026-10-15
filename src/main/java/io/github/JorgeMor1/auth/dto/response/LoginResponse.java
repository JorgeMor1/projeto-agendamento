package io.github.JorgeMor1.auth.dto.response;

import io.github.JorgeMor1.usuarios.enums.UserRole;
import io.github.JorgeMor1.usuarios.enums.UserStatus;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record LoginResponse (
    UserSummaryDTO user,
    TokenResponse tokens
) {}

