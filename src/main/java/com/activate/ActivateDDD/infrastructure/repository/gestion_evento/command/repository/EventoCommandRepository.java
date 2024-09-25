package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.repository;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.EventoCommand;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoCommandRepository extends CrudRepository<EventoCommand, Long> {

    List<EventoCommand> findAllByLastModifiedDateAfter(LocalDateTime lastModifiedDate);

    @Modifying
    @Query("DELETE FROM Participante p WHERE p.evento.id = :eventoId AND p.usuario.id = :usuarioId")
    void eliminarParticipanteDeEvento(@Param("eventoId") Long eventoId, @Param("usuarioId") Long usuarioId);

    @Modifying
    @Query("UPDATE EventoCommand e SET e.lastModifiedDate = :now WHERE e.id = :eventoId")
    void actualizarLastModifiedDate(@Param("eventoId") Long eventoId, @Param("now") LocalDateTime now);
}
