package com.fcamara.hackathonbackend.model;

import javax.persistence.*;

@Entity
public class Habilidade {
    /* ------------------- Propriedades ------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Column(name = "habilidades_possiveis")
    private String habilidadePossivel;
    /* ---------------------------------------------------- */


    /* ------------ Métodos de acessibilidade ------------- */
    public String getHabilidadePossivel() {
        return habilidadePossivel;
    }

    public void setHabilidadePossivel(String habilidadePossivel) {
        this.habilidadePossivel = habilidadePossivel;
    }
    /* ---------------------------------------------------- */
}
