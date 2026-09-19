package io.github.JorgeMor1.usuarios;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("Usuário"),
    ADMIN("Administrador"),
    SUP("Supervisor"),
    ;

    private final String descricao;

    UserRole(String descricao) {
        this.descricao = descricao;
    }
}
