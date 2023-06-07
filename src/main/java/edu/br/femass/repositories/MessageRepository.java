package edu.br.femass.repositories;

import edu.br.femass.entities.Message;
import edu.br.femass.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.from.id = ?1 or m.to.id = ?1")
    List<Message> findMensagemDeUmUsuario(Long id);

    @Query("SELECT m FROM Message m WHERE m.from.id = ?1 and m.to.id = ?2")
    List<Message> findMensagemDeUmUsuarioParaOutro(Long idFrom, Long idTo);
}
