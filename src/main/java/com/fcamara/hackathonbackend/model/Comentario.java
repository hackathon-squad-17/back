package com.fcamara.hackathonbackend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comentario {
    /* ------------------- Propriedades ------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Postagem postagem;

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @Column(nullable = false, name = "conteudo")
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
