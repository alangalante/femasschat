package edu.br.femass.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensagemCriacaoDto {
    private Long idFrom;
    private Long idTo;
    private String mensagem;
}
