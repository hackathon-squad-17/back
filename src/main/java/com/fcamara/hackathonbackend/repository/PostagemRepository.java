package com.fcamara.hackathonbackend.repository;

import com.fcamara.hackathonbackend.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Integer> { }
