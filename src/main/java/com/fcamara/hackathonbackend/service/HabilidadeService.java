package com.fcamara.hackathonbackend.service;

import com.fcamara.hackathonbackend.model.Habilidade;

import java.util.List;
import java.util.Optional;

public interface HabilidadeService {
    public List<Habilidade> listarHabilidadesTodas();

    public Habilidade acessarHabilidade(Optional<Habilidade> habilidadeOptional);

    public List<String> listarHabilidades();
}
