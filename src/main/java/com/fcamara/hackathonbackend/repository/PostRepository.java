package com.fcamara.hackathonbackend.repository;

import com.fcamara.hackathonbackend.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
    // Listar post
    List<Post> findAll();

    // Pesquisar por ID
    Post findById (int id);

    // Remover post
    void delete(Post post);

    // Cadastrar / Alterar post
    <PostMod extends Post> PostMod save (PostMod post);
}
