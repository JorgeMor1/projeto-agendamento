package io.github.JorgeMor1.usuarios.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER( "Usuário" ),
    ADMIN( "Administrador" ),
    SUP( "Supervisor" ),
    MANAGER( "Gerente" );

    private final String name;

    UserRole( String name ) {
        this.name = name;
    }
}
