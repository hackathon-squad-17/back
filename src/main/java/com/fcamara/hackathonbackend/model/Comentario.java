package com.fcamara.hackathonbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Comentario {
    /* ------------------- Propriedades ------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Postagem postagem;

    @ManyToOne(fetch = FetchType.EAGER)
    // @JsonIgnore
    private Usuario usuario;

    @Column(nullable = false, name = "conteudo", length = 1000)
    private String conteudoComentario;

    @Column(nullable = true, name = "data_de_criacao")
    private String dataCriacaoComentario;
    /* ---------------------------------------------------- */


    /* ------------------- Construtores ------------------- */
    public Comentario() {}

    public Comentario(Postagem postagem, Usuario usuario, String conteudoComentario, String dataCriacaoComentario) {
        this.postagem = postagem;
        this.usuario = usuario;
        this.conteudoComentario = conteudoComentario;
        this.dataCriacaoComentario = dataCriacaoComentario;
    }
    /* ---------------------------------------------------- */


    /* ------------ Métodos de acessibilidade ------------- */
    public Postagem getPostagem() {
        return postagem;
    }

    public void setPostagem(Postagem postagem) {
        this.postagem = postagem;
    }

    public String getConteudoComentario() {
        return conteudoComentario;
    }

    public void setConteudoComentario(String conteudoComentario) {
        this.conteudoComentario = conteudoComentario;
    }

    public String getDataCriacaoComentario() {
        return dataCriacaoComentario;
    }

    public void setDataCriacaoComentario(String dataCriacaoComentario) {
        this.dataCriacaoComentario = dataCriacaoComentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    /* ---------------------------------------------------- */
}
