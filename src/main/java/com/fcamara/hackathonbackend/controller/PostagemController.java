package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.Postagem;
import com.fcamara.hackathonbackend.formularios.PostagemForm;
import com.fcamara.hackathonbackend.model.Usuario;
import com.fcamara.hackathonbackend.repository.PostagemRepository;
import com.fcamara.hackathonbackend.repository.UsuarioRepository;
import com.fcamara.hackathonbackend.service.PostagemService;
import com.fcamara.hackathonbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

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

        /*Optional<Usuario> usuarioReferente = usuarioRepository.findByLogin(postagemForm.getLogin());
        if(usuarioReferente.isPresent()){
            Date today = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dataDeHoje = dateFormat.format(today);
            //System.out.println(dataDeHoje);

            Postagem novaPostagem = new Postagem(
                    usuarioReferente.get(),
                    postagemForm.getTitulo(),
                    postagemForm.getCategoria(),
                    postagemForm.getConteudo(),
                    dataDeHoje
            );
            postagemRepository.save(novaPostagem);

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);*/
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

        /*Optional<Postagem> postagem =  postagemRepository.findById(id);
        return postagem.orElseGet(Postagem::new);*/
    }

    /*
        Lista todas as postagens de um login especificado
    */
    /* @GetMapping(path = "/postagens-login")
    public List<Postagem> listarPorLogin(@RequestParam String login) {
        Optional<Usuario> usuarioReferente = usuarioRepository.findByLogin(login);
        if (usuarioReferente.isPresent()){
            return usuarioReferente.get().getPostagem();
        } else {
            return Collections.emptyList();
        }
    } */

    /*
        Busca titulos de postagens, de acordo com a busca inserida, fornecendo sugestoes enquanto a busca eh realizada
    */
    @GetMapping(path = "/busca-postagem-sugestoes") // fornece sugestoes de titulos de acordo com a busca feita
    public List<String> buscarTituloPostagemComSugestoes(@RequestParam String busca) {
        List<String> listaTitulosPostagem = postagemService.listarTitulos();

        return usuarioService.adicionarItensContidos(listaTitulosPostagem, busca);

        /*List<String> listaTitulosPostagem = postagemRepository.findTitulos();

        listaTitulosPostagem.forEach(itemLista -> {
            // Tratamento de acentos
            String itemListaNormalizado = Normalizer.normalize(itemLista, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String itemListaTratado = pattern.matcher(itemListaNormalizado).replaceAll("");

            if (itemListaTratado.toLowerCase().contains(busca))
                sugestoes.add(itemLista);
        });

        return sugestoes;*/
    }

    /*
        Busca conteudos de postagens de acordo com a busca inserida
    */
    @GetMapping(path = "/busca-conteudo-postagem")
    public List<Postagem> buscarConteudoPostagemComSugestoes(@RequestParam String busca) {
        List<String> listaConteudosPostagens = postagemService.listarConteudos();
        List<Integer> listaIdsPostagem = postagemService.listarIds();

        return postagemService.adicionarItensContidosEmConteudo(listaConteudosPostagens, listaIdsPostagem, busca);

        /*List<String> listaConteudosPostagens = postagemRepository.findConteudos();

        listaConteudosPostagens.forEach(itemLista -> {
            // Tratamento de acentos
            String itemListaNormalizado = Normalizer.normalize(itemLista, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String itemListaTratado = pattern.matcher(itemListaNormalizado).replaceAll("");

            if (itemListaTratado.toLowerCase().contains(busca)) {
                int indiceItemLista = listaConteudosPostagens.indexOf(itemLista);
                int indiceItemPostagem = listaIdsPostagem.get(indiceItemLista);

                Optional<Postagem> postagemOptional = postagemRepository.findById(indiceItemPostagem);
                postagemOptional.ifPresent(postagensEncontradas::add);
            }
        });

        return postagensEncontradas;*/
    }

    /*
        Busca postagens de acordo com a categoria inserida
    */
    @GetMapping(path = "/busca-categoria-postagem")
    public List<Postagem> buscarPostagemPorCategoria(@RequestParam String categoria) {
        return postagemService.listarPorCategoria(categoria);

        /*Optional<List<Postagem>> optionalPostagem = postagemRepository.findByCategoria(categoria);
        return optionalPostagem.orElse(Collections.emptyList());*/
    }
}
