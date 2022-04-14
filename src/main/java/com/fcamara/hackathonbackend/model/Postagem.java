package com.fcamara.hackathonbackend.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;

@Entity
public class Postagem {
    /* ------------------- Propriedades ------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @Column(nullable = false, name = "titulo")
    private String titulo;

    @Column(nullable = true)
    private String categoria;

    @Column(nullable = false, name = "conteudo", length = 1000)
    private String conteudoPostagem;

    @Column(nullable = true, name = "data_de_criacao")
    private String dataCriacaoPostagem;

    @OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comentario> comentarios;
    /* ---------------------------------------------------- */


    /* ------------------- Construtores ------------------- */
    public Postagem() {}

    public Postagem(Usuario usuario,
                    String titulo,
                    String categoria,
                    String conteudoPostagem,
                    String dataCriacaoPostagem) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.categoria = categoria;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public int getId() {
        return id;
    }
    /* ---------------------------------------------------- */
}
