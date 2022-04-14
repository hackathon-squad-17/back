package com.fcamara.hackathonbackend.service;

import com.fcamara.hackathonbackend.formularios.ComentarioForm;
import com.fcamara.hackathonbackend.model.Comentario;
import com.fcamara.hackathonbackend.model.Postagem;
import com.fcamara.hackathonbackend.model.Usuario;
import com.fcamara.hackathonbackend.repository.ComentarioRepository;
import com.fcamara.hackathonbackend.repository.PostagemRepository;
import com.fcamara.hackathonbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class ComentarioServiceImpl implements ComentarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PostagemRepository postagemRepository;
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Transactional
    public ResponseEntity<?> criarComentario(Usuario usuario, Postagem postagem, ComentarioForm comentarioForm) {
        if (usuario != null && postagem != null) {
            Date today = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dataDeHoje = dateFormat.format(today);

            Comentario novoComentario = new Comentario(
                    postagem,
                    usuario,
                    comentarioForm.getConteudo(),
                    dataDeHoje
            );
            comentarioRepository.save(novoComentario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Transactional
    public List<Comentario> listarComentariosTodos() {
        return comentarioRepository.findAll();
    }

}
