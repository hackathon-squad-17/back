package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.Comentario;
import com.fcamara.hackathonbackend.formularios.ComentarioForm;
import com.fcamara.hackathonbackend.model.Postagem;
import com.fcamara.hackathonbackend.model.Usuario;
import com.fcamara.hackathonbackend.service.ComentarioService;
import com.fcamara.hackathonbackend.service.PostagemService;
import com.fcamara.hackathonbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/comentarios")
public class ComentarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PostagemService postagemService;
    @Autowired
    private ComentarioService comentarioService;

    /*
        Cria um novo comentario
    */
    @PostMapping(path = "/novo-comentario")
    @CrossOrigin("*")
    public ResponseEntity<?> adicionarComentario(@RequestBody ComentarioForm comentarioForm) {
        Usuario usuario = usuarioService.acessarUsuarioPorLogin(comentarioForm.getLogin());
        Postagem postagem = postagemService.acessarPostagemPorId(comentarioForm.getIdPost());

        return comentarioService.criarComentario(usuario, postagem, comentarioForm);
    }

    /*
        Lista todos os comentarios existentes
    */
    @GetMapping(path = "/todos-comentarios")
    public List<Comentario> listarComentarios() {
        return comentarioService.listarComentariosTodos();
    }
}
