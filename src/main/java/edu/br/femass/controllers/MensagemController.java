package edu.br.femass.controllers;

import edu.br.femass.controllers.dtos.MensagemCriacaoDto;
import edu.br.femass.controllers.dtos.MensagemDto;
import edu.br.femass.controllers.dtos.UsuarioDto;
import edu.br.femass.controllers.dtos.UsuarioSemSenhaDto;
import edu.br.femass.entities.Message;
import edu.br.femass.entities.Usuario;
import edu.br.femass.repositories.MessageRepository;
import edu.br.femass.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
public class MensagemController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MessageRepository messageRepository;
    @CrossOrigin
    @GetMapping("/buscarUsuarios/{login}")
    public List<UsuarioSemSenhaDto> getUsuariosCadastrados(@PathVariable("login") String login) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findByTelefone(login);

        if (!usuario.isPresent()) {
            throw new Exception("Login não autorizado para essa operação");
        }

        List<Usuario> usuarios = usuarioRepository.findAll();

        usuarios.remove(usuario.get());

        ModelMapper modelMapper = new ModelMapper();

        List<UsuarioSemSenhaDto> dtos = usuarios
                .stream()
                .map(user -> modelMapper.map(user, UsuarioSemSenhaDto.class))
                .collect(Collectors.toList());
        return dtos;
    }

    @CrossOrigin
    @GetMapping("/buscarUsuariosComConversa/{id}")
    public Set<UsuarioSemSenhaDto> getUsuariosComConversa(@PathVariable("id") Long id) throws Exception {

        List<Message> mensagens = messageRepository.findMensagemDeUmUsuario(id);

        Set<UsuarioSemSenhaDto> usuarios = new HashSet<>();

        ModelMapper modelMapper = new ModelMapper();

        for (Message m: mensagens) {
            usuarios.add(modelMapper.map(m.getTo(), UsuarioSemSenhaDto.class));
            usuarios.add(modelMapper.map(m.getFrom(), UsuarioSemSenhaDto.class));
        }
        return usuarios;
    }

    @CrossOrigin
    @GetMapping("/buscarMensagensComUmUsuario/{id}/{idOther}")
    public Set<MensagemDto> getMensagens(@PathVariable("id") Long idFrom, @PathVariable("idOther") Long idTo) throws Exception {

        List<Message> mensagens = messageRepository.findMensagemDeUmUsuarioParaOutro(idFrom, idTo);
        List<Message> mensagens2 = messageRepository.findMensagemDeUmUsuarioParaOutro(idTo, idFrom);

        mensagens.addAll(mensagens2);

        ModelMapper modelMapper = new ModelMapper();

        Set<MensagemDto> dtos = mensagens
                .stream()
                .map(msg -> modelMapper.map(msg, MensagemDto.class))
                .collect(Collectors.toSet());
        return dtos;

    }

    @CrossOrigin
    @PostMapping("/enviarMensagem")
    public void enviarMensagem(@RequestBody MensagemCriacaoDto mensagemCriacaoDto) throws Exception {
        Optional<Usuario> usuarioFrom = usuarioRepository.findById(mensagemCriacaoDto.getIdFrom());
        Optional<Usuario> usuarioTo = usuarioRepository.findById(mensagemCriacaoDto.getIdTo());

        if (usuarioFrom.isEmpty()) {
            throw new Exception("Usuário não encontrado para esse chat");
        }

        if (usuarioTo.isEmpty()) {
            throw new Exception("Usuário não encontrado para esse chat");
        }

        Message message = new Message();
        message.setTo(usuarioTo.get());
        message.setFrom(usuarioFrom.get());
        message.setMensagem(mensagemCriacaoDto.getMensagem());
        message.setDataHora(LocalDateTime.now());

        messageRepository.save(message);

    }



}
