package com.fcamara.hackathonbackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario {
    /* ------------------- Propriedades ------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "nome")
    private String nome;

    @Column(nullable = false, name = "login")
    private String login;

    @Column(nullable = false, name = "senha")
    private String password;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(name = "area_de_atuacao")
    private String areaAtuacao;

    @ElementCollection
    private List<String> habilidades;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Postagem> postagens;
    /* ---------------------------------------------------- */


    /* ------------------- Construtores ------------------- */
    public Usuario() {}

    public Usuario(String nome, String login, String password, String email) {
        this.nome = nome;
        this.login = login;
        this.password = password;
        this.email = email;
    }
    /* ---------------------------------------------------- */


    /* ------------ Métodos de acessibilidade ------------- */
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

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades;
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
    /* ---------------------------------------------------- */


    /* --------------- Métodos necessarios ---------------- */
    public void setUmaHabilidade(String habilidade) {
        this.habilidades.add(habilidade);
    }

    @Override
    public String toString() {
        return "Usuário[" +
                "\n\t" + nome +
                "\n\t" + login +
                "\n\t" + password +
                "\n\t" + email +
                "]";
    }
    /* ---------------------------------------------------- */
}
