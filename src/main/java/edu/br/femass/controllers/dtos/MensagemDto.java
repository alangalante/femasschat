package edu.br.femass.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensagemDto {
    private Long id;
    private UsuarioMensagemDto from;
    private UsuarioMensagemDto to;
    private String mensagem;
    private LocalDateTime dataHora;
}
