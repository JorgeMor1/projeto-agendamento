package io.github.JorgeMor1.usuarios;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobreNnome;
    private String email;
    @JsonbTransient
    private String senha;

    public Usuario(String nome, String sobreNnome, String email, String senha) {
        this.nome = nome;
        this.sobreNnome = sobreNnome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNnome() {
        return sobreNnome;
    }

    public void setSobreNnome(String sobreNnome) {
        this.sobreNnome = sobreNnome;
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
