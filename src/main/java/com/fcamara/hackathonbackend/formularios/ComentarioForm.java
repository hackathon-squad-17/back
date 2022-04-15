package com.fcamara.hackathonbackend.formularios;

public class ComentarioForm {
    /* ------------------- Propriedades ------------------- */
    private String login;
    private String conteudo;
    private Integer idPost;
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

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }
    /* ---------------------------------------------------- */
}
