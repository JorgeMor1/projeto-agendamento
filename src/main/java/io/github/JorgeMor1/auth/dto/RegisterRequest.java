package io.github.JorgeMor1.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest (
     @NotBlank(message = "O nome não pode estar em branco")
     @Size(min = 2, max = 100, message = "O nome deve ter entre {min} e {max} caracteres")
     String firstName,

     @NotBlank(message = "O sobrenome não pode estar em branco")
     @Size(min = 2, max = 100, message = "O sobrenome deve ter entre {min} e {max} caracteres")
     String lastName,

     @NotBlank(message = "O e-mail não pode estar em branch")
     @Email(message = "O e-mail deve ser válido")
     String email,

     @NotBlank(message = "A senha não pode estar em branco")
     @Size(min = 8, message = "A senha não deve possui menos de {min} caracteres")
     String password
){}
