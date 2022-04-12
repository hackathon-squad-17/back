package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.Comentario;
import com.fcamara.hackathonbackend.formularios.ComentarioForm;
import com.fcamara.hackathonbackend.model.Postagem;
import com.fcamara.hackathonbackend.model.Usuario;
import com.fcamara.hackathonbackend.repository.ComentarioRepository;
import com.fcamara.hackathonbackend.repository.PostagemRepository;
import com.fcamara.hackathonbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/comentarios")
public class ComentarioController {
    @Autowired
    ComentarioRepository comentarioRepository;
    @Autowired
    PostagemRepository postagemRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    /*
        Cria um novo comentario
    */
    @PostMapping(path = "/novo-comentario")
    @CrossOrigin("*")
    public ResponseEntity<?> adicionarComentario(@RequestBody ComentarioForm comentarioForm) {
        Optional<Postagem> postagem = postagemRepository.findById(comentarioForm.getIdPost());
        Optional<Usuario> usuario = usuarioRepository.findByLogin(comentarioForm.getLogin());

        if (postagem.isPresent() && usuario.isPresent()) {
            Date today = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dataDeHoje = dateFormat.format(today);

            Comentario novoComentario = new Comentario(postagem.get(), usuario.get(), comentarioForm.getConteudo(), dataDeHoje);
            comentarioRepository.save(novoComentario);

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
        Lista todos os comentarios existentes
    */
    @GetMapping(path = "/todos-comentarios")
    public List<Comentario> listarComentarios() {
        return comentarioRepository.findAll();
    }


}
