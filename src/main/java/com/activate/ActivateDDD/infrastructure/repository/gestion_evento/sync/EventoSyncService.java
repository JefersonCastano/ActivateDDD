package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.sync;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.EventoCommand;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.repository.EventoCommandRepository;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.*;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoSyncService {

    LocalDateTime lastSyncDate = LocalDateTime.now();

    @Autowired
    EventoCommandRepository eventoCommandRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private MongoOperations mongoOps;

    @Transactional
    public void sync() {
        updateEvento();
        lastSyncDate = LocalDateTime.now();
    }

    private void updateEvento() {
        List<EventoCommand> modifiedEventos = getEventosASincronizar();
        System.out.println("Eventos a modificar: " + modifiedEventos.size() + " Hora: " + lastSyncDate);
        for(EventoCommand evento: modifiedEventos) {
            Query query = new Query(Criteria.where("id").is(evento.getId().toString()));
            Update update = new Update();

            update.set("aforoMaximo", evento.getAforoMaximo());
            update.set("duracion", evento.getDuracion()); // En minutos
            update.set("nombre", evento.getNombre());
            update.set("descripcion", evento.getDescripcion());
            update.set("fecha", evento.getFecha()); // LocalDateTime
            update.set("ubicacion", evento.getUbicacion()); // Ubicacion: si es un objeto embebido, se actualizará correctamente.
            update.set("estado", evento.getEstado()); // Enum Estado
            update.set("tipo", evento.getTipo()); // Enum TipoEvento
            update.set("organizador", getOrganizador(evento.getOrganizador())); // Objeto Organizador
            update.set("intereses", evento.getIntereses()); // Set<Interes>
            update.set("participantes", getPaticipantes(evento.getParticipantes())); // List<Participante>
            update.set("evaluaciones", getEvaluaciones(evento.getEvaluaciones())); // List<Evaluacion>

            mongoOps.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true).upsert(true), Evento.class);
            System.out.println("Evento " + evento.getId().toString() + " actualizado");
        }
    }

    private List<EventoCommand> getEventosASincronizar() {
        return eventoCommandRepository.findAllByLastModifiedDateAfter(lastSyncDate);
    }

    private Organizador getOrganizador(Long id) {
        String nombre = usuarioRepository.findById(id).get().getNombre();
        return new Organizador(id, nombre);
    }

    private List<Participante> getPaticipantes(List<com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Participante> participantesCommand){
        return participantesCommand.stream().map(participanteCommand -> new Participante(participanteCommand.getId(), participanteCommand.getUsuario().getId(), participanteCommand.getUsuario().getNombre())).collect(Collectors.toList());
    }

    private List<Evaluacion> getEvaluaciones(List<com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Evaluacion> evaluacionesCommand){
        return evaluacionesCommand.stream()
                .map(evaluacionCommand -> {
                    Usuario usuario = usuarioRepository.findById(evaluacionCommand.getAutor()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                    return new Evaluacion(
                            evaluacionCommand.getId(),
                            evaluacionCommand.getComentario(),
                            evaluacionCommand.getPuntuacion(),
                            usuario.getNombre(),
                            usuario.getId()
                    );
                })
                .collect(Collectors.toList());
    }
}
