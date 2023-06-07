package edu.br.femass.controllers.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSemSenhaDto {
    private Long id;
    private String nome;
    private String avatar;
    private String email;
    private String telefone;
}
