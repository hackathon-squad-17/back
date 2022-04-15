package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.Postagem;
import com.fcamara.hackathonbackend.formularios.PostagemForm;
import com.fcamara.hackathonbackend.model.Usuario;
import com.fcamara.hackathonbackend.service.PostagemService;
import com.fcamara.hackathonbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(path = "/postagens")
public class PostagemController {
    @Autowired
    private PostagemService postagemService;
    @Autowired
    private UsuarioService usuarioService;

    /*
        Cria uma nova postagem
    */
    @PostMapping(path = "/nova-postagem")
    @CrossOrigin("*")
    public ResponseEntity<?> adicionarPostagem(@RequestBody PostagemForm postagemForm) {
        Usuario usuarioReferente = usuarioService.acessarUsuarioPorLogin(postagemForm.getLogin());

        if (usuarioReferente != null) {
            Date today = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dataDeHoje = dateFormat.format(today);

            Postagem novaPostagem = new Postagem(
                    usuarioReferente,
                    postagemForm.getTitulo(),
                    postagemForm.getCategoria(),
                    postagemForm.getConteudo(),
                    dataDeHoje
            );

            postagemService.salvarPostagem(novaPostagem);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /*
        Lista todas as postagens existentes
    */
    @GetMapping(path = "/todas-postagens")
    @CrossOrigin("*")
    public List<Postagem> listarPostagens() {
       return postagemService.listarPostagensTodas();
    }

    /*
        Lista postagem do id especificado ou cria uma, caso nao exista
    */
    @GetMapping(path = "/postagem-id")
    @CrossOrigin("*")
    public Postagem listarPostagemId(int id){
        Postagem postagem = postagemService.acessarPostagemPorId(id);

        return Objects.requireNonNullElseGet(postagem, Postagem::new);
    }

    /*
        Busca titulos de postagens, de acordo com a busca inserida, fornecendo sugestoes enquanto a busca eh realizada
    */
    @GetMapping(path = "/busca-postagem-sugestoes") // fornece sugestoes de titulos de acordo com a busca feita
    public List<String> buscarTituloPostagemComSugestoes(@RequestParam String busca) {
        List<String> listaTitulosPostagem = postagemService.listarTitulos();

        return usuarioService.adicionarItensContidos(listaTitulosPostagem, busca);
    }

    /*
        Busca conteudos de postagens de acordo com a busca inserida
    */
    @GetMapping(path = "/busca-conteudo-postagem")
    public List<Postagem> buscarConteudoPostagemComSugestoes(@RequestParam String busca) {
        List<String> listaConteudosPostagens = postagemService.listarConteudos();
        List<Integer> listaIdsPostagem = postagemService.listarIds();

        return postagemService.adicionarItensContidosEmConteudo(listaConteudosPostagens, listaIdsPostagem, busca);
    }

    /*
        Busca postagens de acordo com a categoria inserida
    */
    @GetMapping(path = "/busca-categoria-postagem")
    public List<Postagem> buscarPostagemPorCategoria(@RequestParam String categoria) {
        return postagemService.listarPorCategoria(categoria);
    }
}
