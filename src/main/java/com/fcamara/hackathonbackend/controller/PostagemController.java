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
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(path = "/postagens")
public class PostagemController {
    @Autowired
    private PostagemRepository postagemRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(path = "/todas-postagens")
    public List<Postagem> listarPostagens() {
        return postagemRepository.findAll();
    }

    @GetMapping(path = "/postagens-login")
    public List<Postagem> listarPorLogin(@RequestParam String login) {
        Optional<Usuario> usuarioReferente = usuarioRepository.findByLogin(login);
        if (usuarioReferente.isPresent()){
            return usuarioReferente.get().getPostagem();
        } else {
            return Collections.emptyList();
        }
    }

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
}
