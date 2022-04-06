package com.fcamara.hackathonbackend.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    public Usuario() {}

    public Usuario(String nome, String password, String email) {
        this.nome = nome;
        this.password = password;
        this.email = email;
    }

    /* Metodos de acessibilidade */
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /* ------------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id && nome.equals(usuario.nome) && password.equals(usuario.password) && email.equals(usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, password, email);
    }
}
