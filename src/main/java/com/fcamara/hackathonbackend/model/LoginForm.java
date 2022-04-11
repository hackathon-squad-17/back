package com.fcamara.hackathonbackend.model;

public class LoginForm {
    /* ------------------- Propriedades ------------------- */
    private String loginOuEmail;
    private String senha;
    /* ---------------------------------------------------- */


    /* ------------ Métodos de acessibilidade ------------- */
    public String getLoginOuEmail() {
        return loginOuEmail;
    }

    public void setLoginOuEmail(String loginOuEmail) {
        this.loginOuEmail = loginOuEmail;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    /* ---------------------------------------------------- */
}
