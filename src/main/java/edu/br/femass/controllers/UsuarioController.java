package edu.br.femass.controllers;

import edu.br.femass.controllers.dtos.UsuarioDto;
import edu.br.femass.entities.Usuario;
import edu.br.femass.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @GetMapping("/{login}/{senha}")
    public UsuarioDto getUsuarioByLoginSenha(@PathVariable("login") String login,
                                          @PathVariable("senha") String senha) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findByTelefone(login);

        if (usuario.isPresent() && usuario.get().getSenha().equals(senha)) {
            return new ModelMapper().map(usuario.get(), UsuarioDto.class);
        } else {
            throw new Exception("Usuário não encontrado");
        }

    }

    @GetMapping("/{id}")
    public Integer getHashUsuarioById(@PathVariable("id") Long id) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            return usuario.get().hashCode();
        } else {
            throw new Exception("Usuário não encontrado");
        }

    }

    @DeleteMapping("/{id}")
    public void delUsuarioById(@PathVariable("id") Long id) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
        } else {
            throw new Exception("Usuário não encontrado");
        }

    }

    @PostMapping("/")
    public void gravar(@RequestBody UsuarioDto usuarioDto) {
        ModelMapper modelMapper = new ModelMapper();
        Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
        usuarioRepository.save(usuario);

    }

}
