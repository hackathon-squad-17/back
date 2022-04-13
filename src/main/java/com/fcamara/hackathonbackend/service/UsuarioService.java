package com.fcamara.hackathonbackend.service;

import com.fcamara.hackathonbackend.model.Usuario;
import com.fcamara.hackathonbackend.repository.UsuarioRepository;
import org.apache.catalina.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    public void salvarUsuario(Usuario usuario);

    public Usuario acessarUsuario(Optional<Usuario> usuarioOptional);

    public Usuario acessarUsuarioPorId(int id);

    public Usuario acessarUsuarioPorLogin(String login);

    public Usuario acessarUsuarioPorEmail(String email);

    public List<Usuario> listarUsuariosTodos();

    public List<Usuario> listarUsuariosAreaAtuacao(String areaAtuacao);

    public List<Usuario> listarUsuariosNome(String nome);

    public List<String> listarNomes();

    public List<String> adicionarItensContidos(List<String> lista, String busca);

    public ResponseEntity<byte[]> inserirFoto(String login, String nomeDoArquivo) throws IOException;

    public boolean verificarExistenciaDeLogin(String login);

    public boolean verificarExistenciaDeEmail(String email);

}
