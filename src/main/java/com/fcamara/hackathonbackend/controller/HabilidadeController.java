package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.Habilidade;
import com.fcamara.hackathonbackend.repository.HabilidadeRepository;
import com.fcamara.hackathonbackend.service.HabilidadeService;
import com.fcamara.hackathonbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/habilidades")
public class HabilidadeController {
    @Autowired
    private HabilidadeService habilidadeService;
    @Autowired
    private UsuarioService usuarioService;

    /*
        Lista todas as habilidades existentes
    */
    @GetMapping(path = "/todas-habilidades")
    @CrossOrigin("*")
    public List<Habilidade> listarHabilidades() {
        return habilidadeService.listarHabilidadesTodas();

        //return habilidadeRepository.findAll();
    }

    /*
        Busca habilidades, de acordo com a busca inserida, fornecendo sugestoes enquanto a busca eh realizada
    */
    @GetMapping(path = "/busca-habilidades-sugestoes") // fornece sugestoes de habilidades de acordo com a busca feita
    public List<String> buscarHabilidadesComSugestoes(@RequestParam String busca) {
        List<String> listaHabilidades = habilidadeService.listarHabilidades();

        return usuarioService.adicionarItensContidos(listaHabilidades, busca);

        /*List<String> listaHabilidades = habilidadeRepository.findHabilidadePossivel();
        List<String> sugestoes = new ArrayList<>();
        listaHabilidades.forEach(itemLista -> {
            // Tratamento de acentos
            String itemListaNormalizado = Normalizer.normalize(itemLista, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String itemListaTratado = pattern.matcher(itemListaNormalizado).replaceAll("");

            if (itemListaTratado.toLowerCase().contains(busca))
                sugestoes.add(itemLista);
        });

        return sugestoes;*/
    }
}
