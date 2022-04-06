package com.fcamara.hackathonbackend.repository;

import com.fcamara.hackathonbackend.model.Comentario;
import com.fcamara.hackathonbackend.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComentarioRepository extends CrudRepository<Comentario, Integer> {
    // Listar comentarios
    List<Comentario> findAll();

    // Pesquisar por ID
    Comentario findById (int id);

    // Remover comentario
    void delete(Comentario comentario);

    // Cadastrar / Alterar comentario
    <ComentMod extends Comentario> ComentMod save (ComentMod comentario);
}
