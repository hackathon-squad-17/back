package com.fcamara.hackathonbackend.service;

import com.fcamara.hackathonbackend.model.Habilidade;
import com.fcamara.hackathonbackend.repository.HabilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HabilidadeServiceImpl implements HabilidadeService {
    @Autowired
    private HabilidadeRepository habilidadeRepository;

    @Transactional
    public List<Habilidade> listarHabilidadesTodas() {
        return habilidadeRepository.findAll();
    }

    @Transactional
    public List<String> listarHabilidades() {
        return habilidadeRepository.findHabilidadePossivel();
    }

    @Transactional
    public Habilidade acessarHabilidade(Optional<Habilidade> habilidadeOptional) {
        Habilidade habilidade = new Habilidade();

        if (habilidadeOptional.isPresent())
            habilidade = habilidadeOptional.get();

        return habilidade;
    }
}
