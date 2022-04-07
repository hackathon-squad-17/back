package com.fcamara.hackathonbackend.repository;

import com.fcamara.hackathonbackend.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> { }
