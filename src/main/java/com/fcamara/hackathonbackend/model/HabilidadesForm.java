package com.fcamara.hackathonbackend.model;

import java.util.List;

public class HabilidadesForm {
    private String login;
    private List<String> habilidades;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades;
    }
}
