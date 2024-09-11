package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.service;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evento;
import org.springframework.beans.factory.annotation.Autowired;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.repository.EventoRepository;
import org.springframework.stereotype.Service;

@Service
public class QueryService {
    @Autowired
    private EventoRepository eventoRepository;

    public Evento getEventoById(String id) {
        return eventoRepository.findById(id).orElse(null);
    }

    //Estos dos se tienen que eliminar
    public Evento createEvento(Evento evento) {
    if (evento.getId() != null && eventoRepository.existsById(evento.getId())) {
        throw new IllegalArgumentException("El evento con el ID proporcionado ya existe");
    }
    return eventoRepository.save(evento);
}

    public Evento updateEvento(Evento evento) {
        if (evento.getId() == null || !eventoRepository.existsById(evento.getId())) {
            throw new IllegalArgumentException("El evento con el ID proporcionado no existe");
        }
        return eventoRepository.save(evento);
    }


}
