package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.sync;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

public class EventoSyncService {

    Date lastSyncDate = new Date();

    @Autowired
    EventoCommandRepository eventoCommandRepository;

    @Autowired
    ParticipanteCommandRepository participanteCommandRepository;

    @Autowired
    EvaluacionCommandRepository evaluacionCommandRepository;

    @Autowired
    private MongoOperations mongoOps;

    public void sync() {
        Date newSyncDate = new Date();
        updateEvento();
        updateIntereses();
        updateParticipantes();
        updateEvaluaciones();
        lastSyncDate = newSyncDate;
    }

    private void updateEvento() {
        List<EventoCommand> modifiedEventos = eventoCommandRepository.findAllByLastModifiedDateAfter(lastSyncDate);

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
            update.set("organizador", evento.getOrganizador()); // Objeto Organizador

            mongoOps.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true).upsert(true), Evento.class);
        }
    }

    private void updateIntereses() {
        List<InteresCommand> modifiedIntereses = InteresCommandRepository.findAllByLastModifiedDateAfter(lastSyncDate);

        for(InteresCommand interes : modifiedIntereses) {
            Query query = new Query(Criteria.where("id").is(Interes.getEventoId().toString()));
            Update update = new Update();

            Interes mongoInteres = Interes.valueOf(interes.getInteres());

            update.addToSet("intereses", mongoInteres);

            mongoOps.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true).upsert(true), Evento.class);
        }
    }

    private void updateParticipantes() {
        List<ParticipanteCommand> modifiedParticipantes = participanteCommandRepository.findAllByLastModifiedDateAfter(lastSyncDate);

        for(ParticipanteCommand participante : modifiedParticipantes) {
            Query query = new Query(Criteria.where("id").is(participante.getEventoId().toString()));
            Update update = new Update();

            Participante mongoParticipante = new Participante();
            mongoParticipante.setId(participante.getId().toString());
            mongoParticipante.setNombre(participante.getNombre());

            update.addToSet("participantes", mongoParticipante);

            mongoOps.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true).upsert(true), Evento.class);
        }
    }

    private void updateEvaluaciones() {
        List<EvaluacionesCommand> modifiedEvaluaciones = evaluacionesCommandRepository.findAllByLastModifiedDateAfter(lastSyncDate);

        for(EvaluacionesCommand evaluacion : modifiedEvaluaciones) {
            Query query = new Query(Criteria.where("id").is(evaluacion.getEventoId().toString()));
            Update update = new Update();

            Evaluacion mongoEvaluacion = new Evaluacion();
            mongoEvaluacion.setId(evaluacion.getId().toString());
            mongoEvaluacion.setPuntuacion(evaluacion.getPuntuacion());
            mongoEvaluacion.setComentario(evaluacion.getComentario());
            mongoEvaluacion.setAutor(evaluacion.getAutor());

            update.addToSet("evaluaciones", mongoEvaluacion);

            mongoOps.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true).upsert(true), Evento.class);
        }
    }
    private void updateEvaluacione2s() {
        List<ReactionCommand> modifiedReactions = reactionCommandRepository.findAllByLastModifiedDateAfter(lastSyncDate);

        for(ReactionCommand reaction : modifiedReactions) {
            Query query = new Query(new Criteria().andOperator(
                    Criteria.where("id").is(reaction.getComment().getPostId().toString()),
                    Criteria.where("comments").elemMatch(Criteria.where("id").is(reaction.getCommentId().toString()))
            ));
            Reaction mongoReaction = new Reaction();
            mongoReaction.setId(reaction.getId().toString());
            mongoReaction.setEmoji(reaction.getEmoji());
            Update update = new Update().addToSet("comments.$.reactions", mongoReaction);
            mongoOps.findAndModify(query, update, FindAndModifyOptions.options().upsert(true), Post.class);
        }
    }
}
