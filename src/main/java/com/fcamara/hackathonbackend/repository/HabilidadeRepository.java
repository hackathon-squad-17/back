package com.fcamara.hackathonbackend.repository;

import com.fcamara.hackathonbackend.model.Habilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabilidadeRepository extends JpaRepository<Habilidade, Integer> {
    @Query("SELECT habilidadePossivel FROM Habilidade habilidade")
    List<String> findHabilidadePossivel();
}
