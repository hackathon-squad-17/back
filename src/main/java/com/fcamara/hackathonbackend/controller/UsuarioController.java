package com.fcamara.hackathonbackend.controller;

import org.springframework.util.StringUtils;
import com.fcamara.hackathonbackend.FileUploadUtil;
import com.fcamara.hackathonbackend.model.Usuario;
import com.fcamara.hackathonbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private List<String> areasDeAtuacao = Arrays.asList("Front-end", "Back-end", "FullStack", "UX", "UI","UX/UI");

    /*
        Cria um novo usuario
    */
    @PostMapping("/novo-usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionarUsuario(@RequestParam String nome,
                                              @RequestParam String login,
                                              @RequestParam String password,
                                              @RequestParam String email,
                                              @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String nomeArquivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Usuario novoUsuario = new Usuario(nome, login, password, email, nomeArquivo);
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        String diretorioUpload = "/usuario-fotos/" + usuarioSalvo.getId();

        FileUploadUtil.saveFile(diretorioUpload, nomeArquivo, multipartFile);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    /*
        Insere area de atuacao para um usuario especificado
    */
    @PostMapping(path = "/nova-area-atuacao")
    public ResponseEntity<?> adicionarAreaAtuacao(@RequestParam int idUsuario,
                                                  @RequestParam String areaAtuacao) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setAreaAtuacao(areaAtuacao);
            usuarioRepository.save(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
        Insere habilidade para um usuario especificado
    */
    @PostMapping(path = "/nova-habilidade")
    public ResponseEntity<?> adicionarHabilidade(@RequestParam int idUsuario,
                                                 @RequestParam String habilidade) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setUmaHabilidade(habilidade);
            usuarioRepository.save(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
        Verifica se usuario e senha informados sao compativeis
    */
    @PostMapping(path = "/verificacao-login")
    public ResponseEntity<?> verificarLogin(@RequestParam String loginOuEmail, @RequestParam String senha) {
        Usuario usuario;
        Optional<Usuario> usuarioOptionalEmail = usuarioRepository.findByEmail(loginOuEmail);
        Optional<Usuario> usuarioOptionalLogin = usuarioRepository.findByLogin(loginOuEmail);

        if (usuarioOptionalEmail.isPresent()) {
            usuario = usuarioOptionalEmail.get();
        } else if (usuarioOptionalLogin.isPresent()) {
            usuario = usuarioOptionalLogin.get();
        } else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Usuário não encontrado.");

        String usuarioSenha = usuario.getPassword();

        if (Objects.equals(usuarioSenha, senha))
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Efetuando login...");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Senha incorreta.");
    }

    /*
        Lista todos os usuarios cadastrados
    */
    @GetMapping(path = "/todos-usuarios")
    public List<Usuario> listarUsuarios() { return usuarioRepository.findAll(); }

    /*
        Lista todos os usuarios de uma area especificada
    */
    @GetMapping(path = "/lista-usuarios-area") // retorna a lista completa de usuarios na area informada
    public List<Usuario> listarUsuarioPorArea(@RequestParam String areaAtuacao) {
        Optional<List<Usuario>> optionalUsuarios = usuarioRepository.findByAreaAtuacao(areaAtuacao);

        return optionalUsuarios.orElse(Collections.emptyList());
    }
    /* Acho que nessas buscas nao precisamos colocar tratamento de exception ne? só retornar a lista vazia...
       talvez colocar uma mensagem no front, que nao sei se precisa ser inserida aqui */

    /*
        Busca usuarios por nome especificado
    */
    @GetMapping(path = "/busca-usuario-nome")
    public List<Usuario> buscarUsuarioPorNome(@RequestParam String nome) {
        Optional<List<Usuario>> optionalUsuarios = usuarioRepository.findByNome(nome);

        return optionalUsuarios.orElse(Collections.emptyList());
    }

    /*
        Busca nomes de usuarios, de acordo com a busca inserida, fornecendo sugestoes enquanto a busca eh realizada
    */
    @GetMapping(path = "/busca-nomes-sugestoes") // fornece sugestoes de nomes de acordo com a busca feita
    public List<String> buscarNomesComSugestoes(@RequestParam String busca) {
        List<String> listaNomesUsuarios = usuarioRepository.findNomes();
        List<String> sugestoes = new ArrayList<>();

        listaNomesUsuarios.forEach(itemLista -> {
            // Tratamento de acentos
            String itemListaNormalizado = Normalizer.normalize(itemLista, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String itemListaTratado = pattern.matcher(itemListaNormalizado).replaceAll("");

            if (itemListaTratado.toLowerCase().contains(busca))
                sugestoes.add(itemLista);
        });

        return sugestoes;
    }

    /*
        Busca areas de atuacao, de acordo com a busca inserida, fornecendo sugestoes enquanto a busca eh realizada
    */
    @GetMapping(path = "/busca-areas-sugestoes") // fornece sugestoes de areas de acordo com a busca feita
    public List<String> buscarAreasComSugestoes(@RequestParam String busca) {
        List<String> sugestoes = new ArrayList<>();

        areasDeAtuacao.forEach(itemLista -> {
            // Tratamento de acentos
            String itemListaNormalizado = Normalizer.normalize(itemLista, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String itemListaTratado = pattern.matcher(itemListaNormalizado).replaceAll("");

            if (itemListaTratado.toLowerCase().contains(busca))
                sugestoes.add(itemLista);
        });

        return sugestoes;
    }
}
