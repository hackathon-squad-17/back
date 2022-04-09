package com.fcamara.hackathonbackend.repository;

import com.fcamara.hackathonbackend.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Integer> {
    @Query("SELECT titulo FROM Postagem postagem")
    public List<String> findTitulos();

    @Query("SELECT conteudoPostagem FROM Postagem postagem")
    public List<String> findConteudos();
}
