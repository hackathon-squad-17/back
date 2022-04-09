package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.Postagem;
import com.fcamara.hackathonbackend.model.Usuario;
import com.fcamara.hackathonbackend.repository.PostagemRepository;
import com.fcamara.hackathonbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/postagens")
public class PostagemController {
    @Autowired
    private PostagemRepository postagemRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    /*
        Cria uma nova postagem
    */
    @PostMapping(path = "/nova-postagem")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionarPostagem(@RequestParam String login,
                                               @RequestParam String titulo,
                                               @RequestParam String conteudo) {
        Optional<Usuario> usuarioReferente = usuarioRepository.findByLogin(login);

        if(usuarioReferente.isPresent()){
            Date today = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dataDeHoje = dateFormat.format(today);
            System.out.println(dataDeHoje);

            Postagem novaPostagem = new Postagem(usuarioReferente.get(), titulo, conteudo, dataDeHoje);
            postagemRepository.save(novaPostagem);

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
        Lista todas as postagens existentes
    */
    @GetMapping(path = "/todas-postagens")
    @CrossOrigin("*")
    public List<Postagem> listarPostagens() {
       List<Postagem> teste = postagemRepository.findAll() ;
       return teste;
    }

    /*
        Lista todas as postagens de um login especificado
    */
    /* @GetMapping(path = "/postagens-login")
    public List<Postagem> listarPorLogin(@RequestParam String login) {
        Optional<Usuario> usuarioReferente = usuarioRepository.findByLogin(login);
        if (usuarioReferente.isPresent()){
            return usuarioReferente.get().getPostagem();
        } else {
            return Collections.emptyList();
        }
    } */

    /*
        Busca titulos de postagens, de acordo com a busca inserida, fornecendo sugestoes enquanto a busca eh realizada
    */
    @GetMapping(path = "/busca-postagem-sugestoes") // fornece sugestoes de titulos de acordo com a busca feita
    public List<String> buscarPostagemComSugestoes(@RequestParam String busca) {
        List<String> listaTitulosPostagem = postagemRepository.findTitulos();
        //List<String> listaConteudoPostagem = postagemRepository.findConteudos();
        List<String> sugestoes = new ArrayList<>();

        listaTitulosPostagem.forEach(itemLista -> {
            // Tratamento de acentos
            String itemListaNormalizado = Normalizer.normalize(itemLista, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String itemListaTratado = pattern.matcher(itemListaNormalizado).replaceAll("");

            if (itemListaTratado.toLowerCase().contains(busca))
                sugestoes.add(itemLista);
        });

        return sugestoes;
    }

    /*
        Busca conteudos de postagens de acordo com a busca inserida
    */
    @GetMapping(path = "/busca-conteudo-postagem")
    public List<Postagem> buscarConteudoPostagem(@RequestParam String busca) {
        List<String> listaConteudosPostagens = postagemRepository.findConteudos();
        List<Postagem> postagensEncontradas = new ArrayList<>();

        listaConteudosPostagens.forEach(itemLista -> {
            // Tratamento de acentos
            String itemListaNormalizado = Normalizer.normalize(itemLista, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String itemListaTratado = pattern.matcher(itemListaNormalizado).replaceAll("");

            if (itemListaTratado.toLowerCase().contains(busca)) {
                int indiceItemLista = listaConteudosPostagens.indexOf(itemLista);
                int indiceItemPostagem = indiceItemLista + 1; // os ids comecam em 1

                Optional<Postagem> postagemOptional = postagemRepository.findById(indiceItemPostagem);
                postagemOptional.ifPresent(postagensEncontradas::add);
            }
        });

        return postagensEncontradas;
    }


}
