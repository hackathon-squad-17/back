package com.fcamara.hackathonbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Postagem {
    /* ------------------- Propriedades ------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Usuario usuario;

    @Column(nullable = false, name = "titulo")
    private String titulo;

    @Column(nullable = false, name = "conteudo")
    private String conteudoPostagem;

    @Column(nullable = true, name = "data_de_criacao")
    private String dataCriacaoPostagem;

    @OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comentario> comentarios;
    /* ---------------------------------------------------- */


    /* ------------------- Construtores ------------------- */
    public Postagem() {}

    public Postagem(Usuario usuario, String titulo, String conteudoPostagem, String dataCriacaoPostagem) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.conteudoPostagem = conteudoPostagem;
        this.dataCriacaoPostagem = dataCriacaoPostagem;
    }
    /* ---------------------------------------------------- */


    /* ------------ Métodos de assecibilidade ------------- */
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudoPostagem;
    }

    public void setConteudo(String conteudo) {
        this.conteudoPostagem = conteudo;
    }

    public String getDataCriacao() {
        return dataCriacaoPostagem;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacaoPostagem = dataCriacao;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    /* ---------------------------------------------------- */
}
