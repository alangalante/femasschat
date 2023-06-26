package edu.br.femass.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioMensagemDto {
    private Long id;
    private String nome;
    private String senha;
    private String email;
    private String telefone;
}
