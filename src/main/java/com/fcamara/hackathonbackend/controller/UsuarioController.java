package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.Usuario;
import com.fcamara.hackathonbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(path = "/todos-usuario")
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping("/novo-usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario adicionarUsuario(@RequestParam String nome,
                                    @RequestParam String login,
                                    @RequestParam String password,
                                    @RequestParam String email) {
        Usuario novoUsuario = new Usuario(nome, login, password, email);

        return usuarioRepository.save(novoUsuario);
    }
}
