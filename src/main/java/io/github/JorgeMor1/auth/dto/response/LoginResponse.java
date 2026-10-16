package io.github.JorgeMor1.auth.dto.response;


public record LoginResponse (
    UserSummaryDTO user,
    TokenResponse tokens
) {}

