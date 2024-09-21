package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.repository;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.EventoCommand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoCommandRepository extends CrudRepository<EventoCommand, Long> {

    List<EventoCommand> findAllByLastModifiedDateAfter(LocalDateTime lastModifiedDate);

}
