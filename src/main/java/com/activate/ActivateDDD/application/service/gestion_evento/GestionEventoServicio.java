package com.activate.ActivateDDD.application.service.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_usuario.GestionUsuarioServicio;
import com.activate.ActivateDDD.application.service.gestion_usuario.UsuarioAdapter;
import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Evaluacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Evento;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante;
import com.activate.ActivateDDD.domain.gestion_evento.servicio.EventoServicioDominio;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.EventoCommand;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.repository.EventoCommandRepository;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.repository.EventoQueryRepository;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class GestionEventoServicio {

    @Autowired
    EventoQueryRepository eventoQueryRepository;
    @Autowired
    EventoCommandRepository eventoCommandRepository;
    @Autowired
    EventoAdapter eventoAdapter;

    @Autowired
    EventoServicioDominio eventoServicioDominio;

    @Autowired
    GestionUsuarioServicio gestionUsuarioServicio;
    @Autowired
    UsuarioRepository usuarioRepository;

    public Evento obtenerEvento(Long idEvento) throws Exception {
        com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evento evento = eventoQueryRepository.findById(String.valueOf(idEvento)).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        return eventoAdapter.mapEventoToDomain(evento);
    }
    public ArrayList<Evento> obtenerEventos() throws Exception {
        ArrayList<Evento> eventos = new ArrayList<>();
        for (com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evento evento : eventoQueryRepository.findAll()) {
            eventos.add(eventoAdapter.mapEventoToDomain(evento));
        }
        return eventos;
    }

    public void actualizarTipo(Long idEvento) throws Exception {
        Evento evento = obtenerEvento(idEvento);
        evento.cambiarTipo();
        eventoCommandRepository.save(eventoAdapter.mapEventoToInfrastructure(evento));
    }

    public void actualizarAforoMaximo(Long idEvento, int aforoMaximo) throws Exception {
        Evento evento = obtenerEvento(idEvento);
        evento.actualizarAforoMaximo(aforoMaximo);
        eventoCommandRepository.save(eventoAdapter.mapEventoToInfrastructure(evento));
    }

    public void actualizarFecha(Long idEvento, LocalDateTime fecha) throws Exception {
        Evento evento = obtenerEvento(idEvento);
        evento.actualizarFecha(fecha);
        eventoCommandRepository.save(eventoAdapter.mapEventoToInfrastructure(evento));
    }

    public void agregarEvaluacion(Long idEvento, String comentario, int puntuacion, Long idParticipante) throws Exception {
        Evento evento = obtenerEvento(idEvento);
        com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario usuarioParticipante = gestionUsuarioServicio.obtenerUsuario(idParticipante);
        Participante participante = new Participante(null, usuarioParticipante);

        Evaluacion evaluacion = new Evaluacion(-1L, comentario, puntuacion, participante);
        evento.agregarEvaluacion(evaluacion);

        eventoCommandRepository.save(eventoAdapter.mapEventoToInfrastructure(evento));
    }

    @Transactional
    public void agregarParticipante(Long idEvento, Long idParticipante) throws Exception {
        Evento evento = obtenerEvento(idEvento);

        com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario usuarioParticipante = usuarioRepository.findById(idParticipante).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Usuario usuarioParticipanteDomain = gestionUsuarioServicio.obtenerUsuario(idParticipante);
        Participante participante = new Participante(null, usuarioParticipanteDomain);

        ArrayList<EventoInfo> eventosParticipante = new ArrayList<>();
        for(com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Participante p : usuarioParticipante.getParticipantes()) {
            eventosParticipante.add(eventoServicioDominio.convertirEventoAInfo(eventoAdapter.mapEventoCommandToDomain(p.getEvento())));
        }
        participante.getEventosParticipados().addAll(eventosParticipante);

        if (eventoServicioDominio.agregarParticipante(evento, participante)){
            EventoCommand eventoCommand = eventoAdapter.mapEventoToInfrastructure(evento);
            eventoCommandRepository.save(eventoCommand);
        }
    }
    @Transactional
    public void eliminarParticipante(Long idEvento, Long idParticipante) throws Exception {
        Evento evento = obtenerEvento(idEvento);

        eventoServicioDominio.eliminarParticipante(evento, idParticipante);

        eventoCommandRepository.save(eventoAdapter.mapEventoToInfrastructure(evento));
    }
}
