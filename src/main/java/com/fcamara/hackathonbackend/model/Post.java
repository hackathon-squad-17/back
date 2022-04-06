package com.fcamara.hackathonbackend.model;

import javax.persistence.*;

@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "usuario")
    private Usuario usuario;

    @Column(name = "conteudoPost")
    private String conteudoPost;

    @Column(name = "tituloPost")
    private String tituloPost;

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

    public String getConteudoPost() {
        return conteudoPost;
    }

    public void setConteudoPost(String conteudoPost) {
        this.conteudoPost = conteudoPost;
    }

    public String getTituloPost() {
        return tituloPost;
    }

    public void setTituloPost(String tituloPost) {
        this.tituloPost = tituloPost;
    }
}
