package com.fcamara.hackathonbackend.service;

import com.fcamara.hackathonbackend.model.Postagem;

import java.util.List;
import java.util.Optional;

public interface PostagemService {
    public void salvarPostagem(Postagem postagem);

    public List<Postagem> listarPostagensTodas();

    public Postagem acessarPostagem(Optional<Postagem> postagemOptional);

    public Postagem acessarPostagemPorId(int id);

    public List<String> listarTitulos();

    public List<String> listarConteudos();

    public List<Integer> listarIds();

    public List<Postagem> listarPorCategoria(String categoria);

    public List<Postagem> adicionarItensContidosEmConteudo(List<String> lista, List<Integer> listaIds, String busca);
}
