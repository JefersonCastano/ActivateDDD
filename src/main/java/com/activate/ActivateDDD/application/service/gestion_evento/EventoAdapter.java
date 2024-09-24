package com.activate.ActivateDDD.application.service.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_usuario.GestionUsuarioServicio;
import com.activate.ActivateDDD.application.service.gestion_usuario.UsuarioAdapter;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Evaluacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Evento;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Organizador;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.EventoCommand;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Participante;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.repository.EventoCommandRepository;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.repository.EventoQueryRepository;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class EventoAdapter {

    @Autowired
    EventoCommandRepository eventoCommandRepository;
    @Autowired
    EventoQueryRepository eventoQueryRepository;

    @Autowired
    GestionUsuarioServicio gestionUsuarioServicio;
    @Autowired
    UsuarioRepository usuarioRepository;


    @Transactional
    public EventoCommand mapEventoToInfrastructure(Evento evento) {
        //TODO: Hacerlo con query?
//        com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evento eventoMapped = eventoQueryRepository.findById(String.valueOf(evento.getId()))
//                .orElse(null);

        EventoCommand eventoMapped = eventoCommandRepository.findById(evento.getId())
                .orElse(new EventoCommand());

        eventoMapped.setAforoMaximo(evento.getAforoMaximo());
        eventoMapped.setDuracion(evento.getDuracion());
        eventoMapped.setNombre(evento.getNombre());
        eventoMapped.setDescripcion(evento.getDescripcion());
        eventoMapped.setFecha(evento.getFecha());
        eventoMapped.setUbicacion(new com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Ubicacion(evento.getUbicacion().getLatitud(), evento.getUbicacion().getLongitud()));
        eventoMapped.setTipo(com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.TipoEvento.valueOf(evento.getTipo().toString()));
        eventoMapped.setEstado(com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Estado.valueOf(evento.getEstado().toString()));
        eventoMapped.setOrganizador(evento.getIdOrganizador());

        for (Interes interes : evento.getIntereses()) {
            eventoMapped.getIntereses().clear();
            eventoMapped.getIntereses().add(com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Interes.valueOf(interes.toString()));
        }

        eventoMapped.getParticipantes().removeIf(p -> evento.getParticipantes().stream()
                .noneMatch(ep -> ep.getUsuario().getId().equals(p.getUsuario().getId())));
        mapParticipantesToInfrastructure(evento.getParticipantes(), eventoMapped);

        eventoMapped.getEvaluaciones().removeIf(e -> evento.getEvaluaciones().stream()
                .noneMatch(ee -> ee.getId().equals(e.getId())));
        mapEvaluacionToInfraestructure( evento.getEvaluaciones(), eventoMapped);

        return eventoMapped;
    }
    private void mapParticipantesToInfrastructure(ArrayList<com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante> participantes, EventoCommand eventoCommand) {
        for (com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante participante : participantes) {
            if (eventoCommand.getParticipantes().stream().noneMatch(p -> p.getUsuario().getId().equals(participante.getUsuario().getId()))) {
                Usuario usuarioParticipante = usuarioRepository.findById(participante.getUsuario().getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                Participante p = new Participante();
                p.setUsuario(usuarioParticipante);
                p.setEvento(eventoCommand);
                eventoCommand.getParticipantes().add(p);
            }
        }
    }

    private void mapEvaluacionToInfraestructure(ArrayList<Evaluacion> evaluaciones, EventoCommand eventoCommand) {
        for (Evaluacion evaluacion : evaluaciones) {
            if (eventoCommand.getEvaluaciones().stream().noneMatch(e -> e.getId().equals(evaluacion.getId()))) { //TODO: El id que esta entrando aqui es -1
                Usuario usuarioAutor = usuarioRepository.findById(evaluacion.getAutor().getUsuario().getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Evaluacion e =
                        new com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Evaluacion(-1L,  evaluacion.getComentario(), evaluacion.getPuntuacion(), evaluacion.getAutor().getUsuario().getId(), eventoCommand);
                eventoCommand.getEvaluaciones().add(e);
            }
        }
    }

    public Evento mapEventoToDomain(com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evento evento) throws Exception {
        com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario usuarioOrganizador = gestionUsuarioServicio.obtenerUsuario(evento.getOrganizador().getId());
        Organizador organizador = new Organizador(usuarioOrganizador);

        HashSet<Interes> intereses = new HashSet<>();
        for (com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Interes interes : evento.getIntereses()) {
            intereses.add(Interes.valueOf(interes.toString()));
        }
        Evento e =  new Evento(Long.parseLong(evento.getId()), evento.getAforoMaximo(), evento.getDuracion(), evento.getNombre(), evento.getDescripcion(),
                evento.getFecha(), new com.activate.ActivateDDD.domain.commons.Ubicacion(evento.getUbicacion().getLatitud(), evento.getUbicacion().getLongitud()),
                com.activate.ActivateDDD.domain.commons.Estado.valueOf(evento.getEstado().toString()),
                com.activate.ActivateDDD.domain.commons.TipoEvento.valueOf(evento.getTipo().toString()), organizador, intereses);
        e.getParticipantes().addAll(mapParticipantesToDomain(evento.getParticipantes()));
        e.getEvaluaciones().addAll(mapEvaluacionToDomain(evento.getEvaluaciones()));
        return e;
    }
    private ArrayList<com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante> mapParticipantesToDomain(List<com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Participante> participantes) throws Exception {
        ArrayList<com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante> participantesMapped = new ArrayList<>();
        for (com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Participante participante : participantes) {
            com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario usuarioParticipante = gestionUsuarioServicio.obtenerUsuario(participante.getIdUsuario());
            participantesMapped.add(new com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante(null, usuarioParticipante));
        }
        return participantesMapped;
    }
    private ArrayList<Evaluacion> mapEvaluacionToDomain(List<com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evaluacion> evaluaciones) throws Exception {
        ArrayList<Evaluacion> evaluacionesMapped = new ArrayList<>();
        for (com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evaluacion evaluacion : evaluaciones) {
            com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario usuarioAutor = gestionUsuarioServicio.obtenerUsuario(Long.valueOf(evaluacion.getAutor()));
            com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante participanteAutor = new com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante(null, usuarioAutor);
            Evaluacion e = new Evaluacion(evaluacion.getId(), evaluacion.getComentario(), evaluacion.getPuntuacion(), participanteAutor);
            evaluacionesMapped.add(e);
        }
        return evaluacionesMapped;
    }
    public Evento mapEventoCommandToDomain(EventoCommand evento) throws Exception {
        com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario usuarioOrganizador = gestionUsuarioServicio.obtenerUsuario(evento.getOrganizador());
        Organizador organizador = new Organizador(usuarioOrganizador);

        HashSet<Interes> intereses = new HashSet<>();
        for (com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Interes interes : evento.getIntereses()) {
            intereses.add(Interes.valueOf(interes.toString()));
        }

        return new Evento(evento.getId(), evento.getAforoMaximo(), evento.getDuracion(), evento.getNombre(), evento.getDescripcion(),
                evento.getFecha(), new com.activate.ActivateDDD.domain.commons.Ubicacion(evento.getUbicacion().getLatitud(), evento.getUbicacion().getLongitud()),
                com.activate.ActivateDDD.domain.commons.Estado.valueOf(evento.getEstado().toString()),
                com.activate.ActivateDDD.domain.commons.TipoEvento.valueOf(evento.getTipo().toString()), organizador, intereses);
    }
}
