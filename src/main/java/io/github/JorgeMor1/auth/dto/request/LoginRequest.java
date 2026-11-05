package io.github.JorgeMor1.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest (
    @NotBlank( message = "O e-mail é obrigatório" )
    @Email( message = "O e-mail deve ser válido" )
    String email,

    @NotBlank( message = "A senha é obrigatória" )
    @Size( min = 8, message = "A senha deve ter no mínimo {min} caracteres." )
    String password
) {}
