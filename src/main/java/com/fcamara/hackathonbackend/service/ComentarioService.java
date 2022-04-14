package com.fcamara.hackathonbackend.service;

import com.fcamara.hackathonbackend.formularios.ComentarioForm;
import com.fcamara.hackathonbackend.model.Comentario;
import com.fcamara.hackathonbackend.model.Postagem;
import com.fcamara.hackathonbackend.model.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ComentarioService {

    public ResponseEntity<?> criarComentario(Usuario usuario, Postagem postagem, ComentarioForm comentarioForm);

    public List<Comentario> listarComentariosTodos();
}
