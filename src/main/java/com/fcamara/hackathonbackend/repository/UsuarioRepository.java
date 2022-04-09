package com.fcamara.hackathonbackend.repository;

import com.fcamara.hackathonbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByLogin(String login);

    Optional<List<Usuario>> findByNome(String nome);

    Optional<List<Usuario>> findByAreaAtuacao(String areaAtuacao);

    @Query("SELECT nome FROM Usuario usuario")
    List<String> findNomes();
}
