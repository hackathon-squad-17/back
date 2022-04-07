package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.Comentario;
import com.fcamara.hackathonbackend.model.Postagem;
import com.fcamara.hackathonbackend.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/comentarios")
public class ComentarioController {
    @Autowired
    ComentarioRepository comentarioRepository;

    @GetMapping(path = "/todos-comentarios")
    public List<Comentario> listarComentarios() {
        return comentarioRepository.findAll();
    }

    @PostMapping(path = "/novo-comentario")
    @ResponseStatus(HttpStatus.CREATED)
    public Comentario adicionarComentario(@RequestBody Postagem postagem,
                                          @RequestParam String conteudo) {
        Date today = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataDeHoje = dateFormat.format(today);
        System.out.println(dataDeHoje);
        Comentario novoComentario = new Comentario(postagem, conteudo, dataDeHoje);

        return comentarioRepository.save(novoComentario);
    }
}
