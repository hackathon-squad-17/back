package com.fcamara.hackathonbackend.service;

import com.fcamara.hackathonbackend.repository.UsuarioRepository;
import org.apache.catalina.Service;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class UsuarioService implements Service {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
}
