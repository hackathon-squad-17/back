package com.fcamara.hackathonbackend.model;

public class PostagemForm {
    /* ------------------- Propriedades ------------------- */
    private String login;
    private String conteudo;
    private String titulo;
    private String categoria;
    /* ---------------------------------------------------- */


    /* ------------ Métodos de acessibilidade ------------- */
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
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

    /* ---------------------------------------------------- */
}
