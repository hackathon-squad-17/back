package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.formularios.*;
import com.fcamara.hackathonbackend.formularios.CadastroForm;
import com.fcamara.hackathonbackend.formularios.UsuarioForm;
import com.fcamara.hackathonbackend.model.*;
import com.fcamara.hackathonbackend.repository.UsuarioRepository;
import com.fcamara.hackathonbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;
    private final List<String> areasDeAtuacao = Arrays.asList("Front-end", "Back-end", "FullStack", "UX", "UI","UX/UI");

    /*
        Cria um novo usuario
    */
    @PostMapping("/novo-usuario")
    //@ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    public ResponseEntity<?> adicionarUsuario(@RequestBody CadastroForm cadastroForm) {
        return usuarioService.criarUsuario(cadastroForm);

        /*Usuario novoUsuario = new Usuario(
                cadastroForm.getNome(),
                cadastroForm.getLogin(),
                cadastroForm.getPassword(),
                cadastroForm.getEmail()
        );
        usuarioService.salvarUsuario(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");

        String nomeArquivo = StringUtils.cleanPath(cadastroForm.getImage().getOriginalFilename());
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        String diretorioUpload = "/usuario-fotos/" + usuarioSalvo.getId();

        FileUploadUtil.saveFile(diretorioUpload, nomeArquivo, cadastroForm.getImage()); */
    }

    /*
        Faz o armazenamento da foto de usuário quando o upload eh feito no site
    */
    @PostMapping("/upload-foto")
    @CrossOrigin("*")
    public ResponseEntity<?> salvarFoto(@RequestParam String usuarioLogin,
                                        @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Usuario usuario = usuarioService.acessarUsuarioPorLogin(usuarioLogin);

        if (usuario != null) {
            usuario.setFoto(multipartFile.getBytes());
            usuarioService.salvarUsuario(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Método implementado ANTES
    /*public ResponseEntity<?> salvarFoto(@RequestParam String usuarioLogin,
                                       @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(usuarioLogin);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setFoto(multipartFile.getBytes());
            usuarioRepository.save(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    /*
        Insere area de atuacao para um usuario especificado
    */
    @PostMapping(path = "/nova-area-atuacao")
    @CrossOrigin("*")
    public ResponseEntity<?> adicionarAreaAtuacao(@RequestBody AreaAtuacaoForm areaAtuacaoForm) {
        Usuario usuario = usuarioService.acessarUsuarioPorLogin(areaAtuacaoForm.getLogin());

        if (usuario != null) {
            usuario.setAreaAtuacao(areaAtuacaoForm.getAreaAtuacao());
            usuarioService.salvarUsuario(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        /*Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(areaAtuacaoForm.getLogin());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setAreaAtuacao(areaAtuacaoForm.getAreaAtuacao());
            usuarioRepository.save(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);*/
    }

    /*
        Insere habilidade para um usuario especificado
    */
    @PostMapping(path = "/novas-habilidades")
    @CrossOrigin("*")
    public ResponseEntity<?> adicionarHabilidade(@RequestBody HabilidadesForm habilidadesForm) {
        Usuario usuario = usuarioService.acessarUsuarioPorLogin(habilidadesForm.getLogin());

        if (usuario != null) {
            usuario.setHabilidades(habilidadesForm.getHabilidades());
            usuarioService.salvarUsuario(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        /*Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(habilidadesForm.getLogin());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setHabilidades(habilidadesForm.getHabilidades());
            usuarioRepository.save(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);*/
    }

    /*
        Insere sessão Sobre Mim para um usuario especificado
    */
    @PostMapping(path = "/sobre-mim")
    @CrossOrigin("*")
    public ResponseEntity<?> adicionarSobreMim(@RequestBody SobreMimForm sobreMimForm) {
        Usuario usuario = usuarioService.acessarUsuarioPorLogin(sobreMimForm.getLogin());

        if (usuario != null) {
            usuario.setSobreMim(sobreMimForm.getSobreMim());
            usuarioService.salvarUsuario(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        /*Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(sobreMimForm.getLogin());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setSobreMim(sobreMimForm.getSobreMim());
            usuarioRepository.save(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);*/
    }

    /*
        Verifica se usuario e senha informados sao compativeis
    */
    @PostMapping(path = "/verificacao-login")
    @CrossOrigin("*")
    public ResponseEntity<?> verificarLogin(@RequestBody LoginForm loginForm) {
        Usuario usuarioLogin = usuarioService.acessarUsuarioPorLogin(loginForm.getLoginOuEmail());
        Usuario usuarioEmail = usuarioService.acessarUsuarioPorEmail(loginForm.getLoginOuEmail());

        Usuario usuario;
        if (usuarioLogin != null)
            usuario = usuarioLogin;
        else if (usuarioEmail != null)
            usuario = usuarioEmail;
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        String usuarioSenha = usuario.getPassword();

        if (Objects.equals(usuarioSenha, loginForm.getSenha()))
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
            //return ResponseEntity.status(HttpStatus.OK).body(usuario.getLogin());
        else
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);

        /*Optional<Usuario> usuarioOptionalEmail = usuarioRepository.findByEmail(loginForm.getLoginOuEmail());
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Senha incorreta.");*/
    }

    /*
        Lista todos os usuarios cadastrados
    */
    @GetMapping(path = "/todos-usuarios")
    @CrossOrigin("*")
    public List<Usuario> listarUsuarios() { return usuarioService.listarUsuariosTodos(); }

    /*
        Encontra usuario pelo login
    */
    @GetMapping(path = "/encontra-usuario-login")
    @CrossOrigin("*")
    public Usuario encontrarUsuario(String login) {
        return usuarioService.acessarUsuarioPorLogin(login);
    }

    /*
        Lista todos os usuarios de uma area especificada
    */
    @GetMapping(path = "/lista-usuarios-area") // retorna a lista completa de usuarios na area informada
    public List<Usuario> listarUsuarioPorArea(@RequestParam String areaAtuacao) {
        return usuarioService.listarUsuariosAreaAtuacao(areaAtuacao);

        /*Optional<List<Usuario>> optionalUsuarios = usuarioRepository.findByAreaAtuacao(areaAtuacao);
        return optionalUsuarios.orElse(Collections.emptyList());*/
    }

    /*
        Busca usuarios por nome especificado
    */
    @GetMapping(path = "/busca-usuario-nome")
    public List<Usuario> buscarUsuarioPorNome(@RequestParam String nome) {
        return usuarioService.listarUsuariosNome(nome);

        /*Optional<List<Usuario>> optionalUsuarios = usuarioRepository.findByNome(nome);
        return optionalUsuarios.orElse(Collections.emptyList());*/
    }

    /*
        Busca nomes de usuarios, de acordo com a busca inserida, fornecendo sugestoes enquanto a busca eh realizada
    */
    @GetMapping(path = "/busca-nomes-sugestoes")
    public List<String> buscarNomesComSugestoes(@RequestParam String busca) {
        List<String> listaNomesUsuarios = usuarioService.listarNomes();

        return usuarioService.adicionarItensContidos(listaNomesUsuarios, busca);

        /*List<String> listaNomesUsuarios = usuarioRepository.findNomes();
        List<String> sugestoes = new ArrayList<>();

        listaNomesUsuarios.forEach(itemLista -> {
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
        Busca areas de atuacao, de acordo com a busca inserida, fornecendo sugestoes enquanto a busca eh realizada
    */
    @GetMapping(path = "/busca-areas-sugestoes")
    public List<String> buscarAreasComSugestoes(@RequestParam String busca) {
        return usuarioService.adicionarItensContidos(areasDeAtuacao, busca);

        /*List<String> sugestoes = new ArrayList<>();

        areasDeAtuacao.forEach(itemLista -> {
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

    /*
        Carrega foto de perfil do usuario do banco de dados
    */
    @RequestMapping(
            value = "/foto-perfil",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @CrossOrigin("*")
    public ResponseEntity<?> carregarImagem(@RequestParam String login) throws IOException {
        Usuario usuario = usuarioService.acessarUsuarioPorLogin(login);

        if (usuario != null) {
            byte[] imgBytes = usuario.getFoto();

            if (imgBytes == null) {
                var imgFile = new PathResource("src/main/resources/imagens/perfil-teste.jpg");
                byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
            } else
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imgBytes);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Método implementado ANTES
    /*public ResponseEntity<byte[]> getImage(@RequestParam String login) throws IOException {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(login);
        if(usuario.isPresent()) {
            byte[] imgBytes = usuario.get().getFoto();
           if(imgBytes == null){
               var imgFile = new PathResource("src/main/resources/imagens/perfil-teste.jpg");
               byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
               return ResponseEntity
                       .ok()
                       .contentType(MediaType.IMAGE_JPEG)
                       .body(bytes);
           } else {
               return ResponseEntity
                       .ok()
                       .contentType(MediaType.IMAGE_JPEG)
                       .body(imgBytes);
           }
        }
        return (ResponseEntity<byte[]>) ResponseEntity.status(HttpStatus.NOT_FOUND);
    }*/

    /*
        Permite que o nome do usuario seja editado
    */
    @PutMapping(path = "/edita-nome-usuario")
    @CrossOrigin("*")
    public ResponseEntity<?> editarNomeUsuario(@RequestBody UsuarioForm usuarioForm,
                                               @RequestParam String novoNome) {
        Usuario usuario = usuarioService.acessarUsuarioPorLogin(usuarioForm.getLogin());

        if (usuario != null) {
            usuario.setNome(novoNome);
            usuarioService.salvarUsuario(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        /*Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(usuarioForm.getLogin());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setNome(novoNome);
            usuarioRepository.save(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);*/
    }

    /*
        Permite que o login do usuario seja editado
    */
    @PutMapping(path = "/edita-login-usuario")
    @CrossOrigin("*")
    public ResponseEntity<?> editarLoginUsuario(@RequestBody UsuarioForm usuarioForm,
                                                @RequestParam String novoLogin) {
        Usuario usuario = usuarioService.acessarUsuarioPorLogin(usuarioForm.getLogin());

        if (usuario != null) {
            if (usuarioService.verificarExistenciaDeLogin(novoLogin))
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            else {
                usuario.setLogin(novoLogin);
                usuarioService.salvarUsuario(usuario);

                return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
            }
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        /*Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(usuarioForm.getLogin());
        if (usuarioOptional.isPresent()) {
            Optional<Usuario> usuarioOptionalValidacao = usuarioRepository.findByLogin(novoLogin);

            if (usuarioOptionalValidacao.isPresent())
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            else {
                Usuario usuario = usuarioOptional.get();
                usuario.setLogin(novoLogin);
                usuarioRepository.save(usuario);

                return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
            }
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);*/
    }

    /*
        Permite que a senha do usuario seja editada
    */
    @PutMapping(path = "/edita-senha-usuario")
    public ResponseEntity<?> editarSenhaUsuario(@RequestBody UsuarioForm usuarioForm,
                                                @RequestParam String novaSenha) {
        Usuario usuario = usuarioService.acessarUsuarioPorLogin(usuarioForm.getLogin());

        if (usuario != null) {
            usuario.setPassword(novaSenha);
            usuarioService.salvarUsuario(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        /*Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(usuarioForm.getLogin());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setPassword(novaSenha);
            usuarioRepository.save(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);*/
    }

    /*
        Permite que o email do usuario seja editado
    */
    @PutMapping(path = "/edita-email-usuario")
    public ResponseEntity<?> editarEmailUsuario(@RequestBody UsuarioForm usuarioForm,
                                                @RequestParam String novoEmail) {
        Usuario usuario = usuarioService.acessarUsuarioPorLogin(usuarioForm.getLogin());

        if (usuario != null) {
            if (usuarioService.verificarExistenciaDeEmail(novoEmail))
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            else {
                usuario.setEmail(novoEmail);
                usuarioService.salvarUsuario(usuario);

                return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
            }
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        /*Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(usuarioForm.getLogin());
        if (usuarioOptional.isPresent()) {
            Optional<Usuario> usuarioOptionalValidacao = usuarioRepository.findByEmail(novoEmail);

            if (usuarioOptionalValidacao.isPresent())
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            else {
                Usuario usuario = usuarioOptional.get();
                usuario.setEmail(novoEmail);
                usuarioRepository.save(usuario);

                return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
            }
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);*/
    }

    /*
        Permite que a Area de Atuacao do usuario seja editada
    */
    @PutMapping(path = "/edita-area-atuacao-usuario")
    public ResponseEntity<?> editarAreaAtuacaoUsuario(@RequestBody UsuarioForm usuarioForm,
                                                      @RequestParam String novaAreaAtuacao) {
        Usuario usuario = usuarioService.acessarUsuarioPorLogin(usuarioForm.getLogin());

        if (usuario != null) {
            usuario.setAreaAtuacao(novaAreaAtuacao);
            usuarioService.salvarUsuario(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        /*Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(usuarioForm.getLogin());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setAreaAtuacao(novaAreaAtuacao);
            usuarioRepository.save(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);*/
    }

    /*
        Permite que o Sobre Mim do usuario seja editado
    */
    @PutMapping(path = "/edita-sobre-mim-usuario")
    public ResponseEntity<?> editarSobreMimUsuario(@RequestBody UsuarioForm usuarioForm,
                                                   @RequestParam String novoSobreMim) {
        Usuario usuario = usuarioService.acessarUsuarioPorLogin(usuarioForm.getLogin());

        if (usuario != null) {
            usuario.setSobreMim(novoSobreMim);
            usuarioService.salvarUsuario(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        /*Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(usuarioForm.getLogin());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setSobreMim(novoSobreMim);
            usuarioRepository.save(usuario);

            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);*/
    }
}
