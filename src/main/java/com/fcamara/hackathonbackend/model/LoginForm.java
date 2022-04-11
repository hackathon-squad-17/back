package com.fcamara.hackathonbackend.model;

public class LoginForm {
    private String loginOuEmail;
    private String senha;

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
}
