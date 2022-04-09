package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.Habilidade;
import com.fcamara.hackathonbackend.repository.HabilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/habilidades")
public class HabilidadeController {
    @Autowired
    HabilidadeRepository habilidadeRepository;

    /*
        Lista todas as habilidades existentes
    */
    @GetMapping(path = "/todas-habilidades")
    public List<Habilidade> listarHabilidades() {
        return habilidadeRepository.findAll();
    }

    /*
        Busca habilidades, de acordo com a busca inserida, fornecendo sugestoes enquanto a busca eh realizada
    */
    @GetMapping(path = "/busca-habilidades-sugestoes") // fornece sugestoes de habilidades de acordo com a busca feita
    public List<String> buscarHabilidadesComSugestoes(@RequestParam String busca) {
        List<String> listaHabilidades = habilidadeRepository.findHabilidadePossivel();
        List<String> sugestoes = new ArrayList<>();

        listaHabilidades.forEach(itemLista -> {
            // Tratamento de acentos
            String itemListaNormalizado = Normalizer.normalize(itemLista, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String itemListaTratado = pattern.matcher(itemListaNormalizado).replaceAll("");

            if (itemListaTratado.toLowerCase().contains(busca))
                sugestoes.add(itemLista);
        });

        return sugestoes;
    }
}
