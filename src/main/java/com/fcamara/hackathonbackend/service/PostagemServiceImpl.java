package com.fcamara.hackathonbackend.service;

import com.fcamara.hackathonbackend.model.Postagem;
import com.fcamara.hackathonbackend.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PostagemServiceImpl implements PostagemService {
    @Autowired
    private PostagemRepository postagemRepository;

    @Transactional
    public void salvarPostagem(Postagem postagem) {
        postagemRepository.save(postagem);
    }

    @Transactional
    public List<Postagem> listarPostagensTodas() {
        return postagemRepository.findAll();
    }

    @Transactional
    public Postagem acessarPostagem(Optional<Postagem> postagemOptional) {
        Postagem postagem = new Postagem();

        if (postagemOptional.isPresent())
            postagem = postagemOptional.get();

        return postagem;
    }

    @Transactional
    public Postagem acessarPostagemPorId(int id) {
        Optional<Postagem> postagemOptional = postagemRepository.findById(id);

        return acessarPostagem(postagemOptional);
    }

    @Transactional
    public List<String> listarTitulos() {
        return postagemRepository.findTitulos();
    }

    @Transactional
    public List<String> listarConteudos() {
        return postagemRepository.findConteudos();
    }

    @Transactional
    public List<Integer> listarIds() {
        return postagemRepository.findIds();
    }

    @Transactional
    public List<Postagem> listarPorCategoria(String categoria) {
        Optional<List<Postagem>> postagemOptional = postagemRepository.findByCategoria(categoria);

        return postagemOptional.orElse(Collections.emptyList());
    }

    @Transactional
    public List<Postagem> adicionarItensContidosEmConteudo(List<String> lista, List<Integer> listaIds, String busca) {
        List<Postagem> postagensEncontradas = new ArrayList<>();

        lista.forEach(itemLista -> {
            // Tratamento de acentos
            String itemListaNormalizado = Normalizer.normalize(itemLista, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String itemListaTratado = pattern.matcher(itemListaNormalizado).replaceAll("");

            if (itemListaTratado.toLowerCase().contains(busca)) {
                int indiceItemLista = lista.indexOf(itemLista);
                int indiceItemPostagem = listaIds.get(indiceItemLista);

                Optional<Postagem> postagemOptional = postagemRepository.findById(indiceItemPostagem);
                postagemOptional.ifPresent(postagensEncontradas::add);
            }
        });

        return postagensEncontradas;
    }
}
