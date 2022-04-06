package com.fcamara.hackathonbackend.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Usuario usuario;

    @Column(name = "conteudoComentario")
    private String conteudoPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;


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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

