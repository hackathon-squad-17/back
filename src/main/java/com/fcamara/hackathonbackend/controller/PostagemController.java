package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.Postagem;
import com.fcamara.hackathonbackend.model.Usuario;
import com.fcamara.hackathonbackend.repository.PostagemRepository;
import com.fcamara.hackathonbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        Usuario usuarioReferente = usuarioRepository.findByLogin(login);

        return usuarioReferente.getPostagem();
    }

    @PostMapping(path = "/nova-postagem")
    @ResponseStatus(HttpStatus.CREATED)
    public Postagem adicionarPostagem(@RequestParam String login,
                                      @RequestParam String titulo,
                                      @RequestParam String conteudo) {
        Usuario usuarioReferente = usuarioRepository.findByLogin(login);

        Date today = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataDeHoje = dateFormat.format(today);
        System.out.println(dataDeHoje);

        Postagem novaPostagem = new Postagem(usuarioReferente, titulo, conteudo, dataDeHoje);

        return postagemRepository.save(novaPostagem);
    }
}
