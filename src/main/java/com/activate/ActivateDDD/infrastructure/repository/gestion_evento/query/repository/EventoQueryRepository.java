package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.repository;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evento;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface EventoQueryRepository extends MongoRepository<Evento, String> {
}
