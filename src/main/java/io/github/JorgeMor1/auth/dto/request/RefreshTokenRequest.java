package io.github.JorgeMor1.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
    @NotBlank( message = "O refresh token é obrigatório" )
    String refreshToken
){}
