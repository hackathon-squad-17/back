package com.fcamara.hackathonbackend.model;

import java.util.List;

public class HabilidadesForm {
    /* ------------------- Propriedades ------------------- */
    private String login;
    private List<String> habilidades;
    /* ---------------------------------------------------- */


    /* ------------ Métodos de acessibilidade ------------- */
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
    /* ---------------------------------------------------- */
}
