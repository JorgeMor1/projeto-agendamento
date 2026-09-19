package io.github.JorgeMor1.usuarios;

import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String role;

    public User() {
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(String nome, String sobreNnome, String email, String senha, String role) {
        this.nome = nome;
        this.sobrenome = sobreNnome;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
