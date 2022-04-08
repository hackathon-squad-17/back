package com.fcamara.hackathonbackend.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario {
    /* Propriedades */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    //@NaturalId
    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Postagem> postagens; // eh necessario que seja uma colecao
    /* ------------------------- */

    /* Construtores */
    public Usuario() {}

    public Usuario(String nome, String login, String password, String email) {
        this.nome = nome;
        this.login = login;
        this.password = password;
        this.email = email;
    }
    /* ------------------------- */

    /* Metodos de acessibilidade */
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public List<Postagem> getPostagem() {
        return postagens;
    }

    public void setPostagem(List<Postagem> postagem) {
        this.postagens = postagem;
    }

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
    /* ------------------------- */

    /* Metodos necessarios */
    @Override
    public String toString() {
        return "Usuário[" +
                "\n\t" + nome +
                "\n\t" + login +
                "\n\t" + password +
                "\n\t" + email +
                "]";
    }


}
