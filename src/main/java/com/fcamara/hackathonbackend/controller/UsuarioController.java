package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.*;
import com.fcamara.hackathonbackend.FileUploadUtil;
import com.fcamara.hackathonbackend.repository.UsuarioRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
    @CrossOrigin("*")
    public ResponseEntity<?> adicionarUsuario(@RequestBody CadastroForm cadastroForm) throws IOException {
        // String nomeArquivo = StringUtils.cleanPath(cadastroForm.getImage().getOriginalFilename());

        Usuario novoUsuario = new Usuario(
                cadastroForm.getNome(),
                cadastroForm.getLogin(),
                cadastroForm.getPassword(),
                cadastroForm.getEmail(),
                cadastroForm.getSobreMim()
        );
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        /* String diretorioUpload = "/usuario-fotos/" + usuarioSalvo.getId();

        FileUploadUtil.saveFile(diretorioUpload, nomeArquivo, cadastroForm.getImage()); */

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    /*
        Faz o armazenamento da foto de usuário quando o upload eh feito no site
    */
    @PostMapping("/upload-foto")
    @CrossOrigin("*")
    public ResponseEntity<?> salvarFoto(@RequestParam String usuarioLogin,
                                        @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(usuarioLogin);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setFoto(fileName);
            usuarioRepository.save(usuario);

            String uploadDir = "user-photos/" + usuario.getLogin();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
        Insere area de atuacao para um usuario especificado
    */
    @PostMapping(path = "/nova-area-atuacao")
    @CrossOrigin("*")
    public ResponseEntity<?> adicionarAreaAtuacao(@RequestBody AreaAtuacaoForm areaAtuacaoForm) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(areaAtuacaoForm.getLogin());

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setAreaAtuacao(areaAtuacaoForm.getAreaAtuacao());
            usuarioRepository.save(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
        Insere habilidade para um usuario especificado
    */
    @PostMapping(path = "/novas-habilidades")
    @CrossOrigin("*")
    public ResponseEntity<?> adicionarHabilidade(@RequestBody HabilidadesForm habilidadesForm) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(habilidadesForm.getLogin());

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setHabilidades(habilidadesForm.getHabilidades());
            usuarioRepository.save(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
        Verifica se usuario e senha informados sao compativeis
    */
    @PostMapping(path = "/verificacao-login")
    @CrossOrigin("*")
    public ResponseEntity<?> verificarLogin(@RequestBody LoginForm loginForm) {
        Usuario usuario;
        Optional<Usuario> usuarioOptionalEmail = usuarioRepository.findByEmail(loginForm.getLoginOuEmail());
        Optional<Usuario> usuarioOptionalLogin = usuarioRepository.findByLogin(loginForm.getLoginOuEmail());

        if (usuarioOptionalEmail.isPresent()) {
            usuario = usuarioOptionalEmail.get();
        } else if (usuarioOptionalLogin.isPresent()) {
            usuario = usuarioOptionalLogin.get();
        } else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Usuário não encontrado.");

        String usuarioSenha = usuario.getPassword();

        if (Objects.equals(usuarioSenha, loginForm.getSenha()))
            return ResponseEntity.status(HttpStatus.OK).body(usuario.getLogin());
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Senha incorreta.");
    }

    /*
        Lista todos os usuarios cadastrados
    */
    @GetMapping(path = "/todos-usuarios")
    @CrossOrigin("*")
    public List<Usuario> listarUsuarios() { return usuarioRepository.findAll(); }

    /*
           Encontra usuario pelo login
       */
    @GetMapping(path = "/encontra-usuario-login")
    @CrossOrigin("*")
    public Optional<Usuario> encontrarUsuario(String login) {
        return usuarioRepository.findByLogin(login);
    }

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

    /*
        Retorna a imagem de perfil do usuario para ser carregada na pagina
    */

    /*
    @GetMapping(
            path = "/download-foto",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
  public @ResponseBody byte[] carregarImagem() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("imagens/perfil-teste.jpg");

        if (inputStream == null)
            return null;

        return IOUtils.toByteArray(inputStream);
    } */

    @RequestMapping(value = "/foto-perfil", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    @CrossOrigin("*")
    public ResponseEntity<byte[]> getImage(@RequestParam String login) throws IOException {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(login);
        if(usuario.isPresent()) {
            String nomeDoArquivo = usuario.get().getFoto();
           if(nomeDoArquivo == null){
               var imgFile = new PathResource("user-photos/default-profile-pic.jpg");
               byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
               return ResponseEntity
                       .ok()
                       .contentType(MediaType.IMAGE_JPEG)
                       .body(bytes);
           } else {
               var imgFile = new PathResource("user-photos/" + login + "/" + nomeDoArquivo);
               byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
               return ResponseEntity
                       .ok()
                       .contentType(MediaType.IMAGE_JPEG)
                       .body(bytes);
           }
        }
        return (ResponseEntity<byte[]>) ResponseEntity.status(HttpStatus.NOT_FOUND);
    }
}
