package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.Comentario;
import com.fcamara.hackathonbackend.model.Post;
import com.fcamara.hackathonbackend.repository.ComentarioRepository;
import com.fcamara.hackathonbackend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {
    @Autowired
    private ComentarioRepository actions;

    @GetMapping
    public @ResponseBody
    List<Comentario> listar(){
        return actions.findAll();
    }

    @PostMapping
    public @ResponseBody Comentario cadastrar(@RequestBody Comentario comentario) {
        return actions.save(comentario);

    }
}
