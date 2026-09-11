package io.github.JorgeMor1.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioRequestDTO {
    private String nome;
    private String sobreNnome;
    private String email;
    private String senha;
}
