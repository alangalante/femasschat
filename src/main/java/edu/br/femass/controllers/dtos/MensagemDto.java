package edu.br.femass.controllers.dtos;

import edu.br.femass.entities.Usuario;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensagemDto {
    private Long id;
    private Usuario from;
    private Usuario to;
    private String mensagem;
    private LocalDateTime dataHora;
}
