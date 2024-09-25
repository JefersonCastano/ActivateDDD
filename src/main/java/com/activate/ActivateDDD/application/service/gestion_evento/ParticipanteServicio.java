package com.activate.ActivateDDD.application.service.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_usuario.GestionUsuarioServicio;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Evento;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante;
import com.activate.ActivateDDD.domain.gestion_evento.servicio.EventoServicioDominio;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ParticipanteServicio {

    GestionUsuarioServicio gestionUsuarioServicio;
    UsuarioRepository usuarioRepository;
    GestionEventoServicio gestionEventoServicio;
    EventoServicioDominio eventoServicioDominio;
    EventoAdapter eventoAdapter;

    @Autowired
    public ParticipanteServicio(GestionUsuarioServicio gestionUsuarioServicio, UsuarioRepository usuarioRepository, @Lazy GestionEventoServicio gestionEventoServicio, EventoServicioDominio eventoServicioDominio, EventoAdapter eventoAdapter) {
        this.gestionUsuarioServicio = gestionUsuarioServicio;
        this.usuarioRepository = usuarioRepository;
        this.gestionEventoServicio = gestionEventoServicio;
        this.eventoServicioDominio = eventoServicioDominio;
        this.eventoAdapter = eventoAdapter;
    }

    public ArrayList<EventoInfo> obtenerEventosParticipante(Long idParticipante) throws Exception {

        Usuario usuarioParticipante = usuarioRepository.findById(idParticipante).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ArrayList<EventoInfo> eventosParticipante = new ArrayList<>();
        for(com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Participante p : usuarioParticipante.getParticipantes()) {
            eventosParticipante.add(eventoServicioDominio.convertirEventoAInfo(eventoAdapter.mapEventoCommandToDomain(p.getEvento())));
        }

        return eventosParticipante;
    }
}
