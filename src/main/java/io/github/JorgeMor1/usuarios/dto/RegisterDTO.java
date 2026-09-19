package io.github.JorgeMor1.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDTO {
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String role;
}
