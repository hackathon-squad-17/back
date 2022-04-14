package com.fcamara.hackathonbackend.service;

import com.fcamara.hackathonbackend.formularios.CadastroForm;
import com.fcamara.hackathonbackend.model.Usuario;
import com.fcamara.hackathonbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void salvarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Transactional
    public ResponseEntity<?> criarUsuario(CadastroForm cadastroForm) {
        Usuario novoUsuario = new Usuario(
                cadastroForm.getNome(),
                cadastroForm.getLogin(),
                cadastroForm.getPassword(),
                cadastroForm.getEmail()
        );
        usuarioRepository.save(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
    }

    @Transactional
    public Usuario acessarUsuario(Optional<Usuario> usuarioOptional) {
        Usuario usuario = new Usuario();

        if (usuarioOptional.isPresent())
            usuario = usuarioOptional.get();

        return usuario;
    }

    @Transactional
    public Usuario acessarUsuarioPorId(int id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        return acessarUsuario(usuarioOptional);
    }

    @Transactional
    public Usuario acessarUsuarioPorLogin(String login) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(login);

        return acessarUsuario(usuarioOptional);
    }

    @Transactional
    public Usuario acessarUsuarioPorEmail(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        return acessarUsuario(usuarioOptional);
    }

    @Transactional
    public List<Usuario> listarUsuariosTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public List<Usuario> listarUsuariosAreaAtuacao(String areaAtuacao) {
        Optional<List<Usuario>> usuarioOptional = usuarioRepository.findByAreaAtuacao(areaAtuacao);

        return usuarioOptional.orElse(Collections.emptyList());
    }

    @Transactional
    public List<Usuario> listarUsuariosNome(String nome) {
        Optional<List<Usuario>> usuarioOptional = usuarioRepository.findByNome(nome);

        return usuarioOptional.orElse(Collections.emptyList());
    }

    @Transactional
    public List<String> listarNomes() {
        return usuarioRepository.findNomes();
    }

    @Transactional
    public List<String> adicionarItensContidos(List<String> lista, String busca) {
        List<String> sugestoes = new ArrayList<>();

        lista.forEach(itemLista -> {
            // Tratamento de acentos
            String itemListaNormalizado = Normalizer.normalize(itemLista, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String itemListaTratado = pattern.matcher(itemListaNormalizado).replaceAll("");

            if (itemListaTratado.toLowerCase().contains(busca))
                sugestoes.add(itemLista);
        });

        return sugestoes;
    }

    @Transactional
    public ResponseEntity<byte[]> inserirFoto(String login, String nomeDoArquivo) throws IOException {
        if(nomeDoArquivo == null){
            PathResource imgFile = new PathResource("user-photos/default-profile-pic.jpg");
            byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } else {
            PathResource imgFile = new PathResource("user-photos/" + login + "/" + nomeDoArquivo);
            byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }
    }

    @Transactional
    public boolean verificarExistenciaDeLogin(String login) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(login);

        return usuarioOptional.isPresent();
    }

    @Transactional
    public boolean verificarExistenciaDeEmail(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        return usuarioOptional.isPresent();
    }
}
