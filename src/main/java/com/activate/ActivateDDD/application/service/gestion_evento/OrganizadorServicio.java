package com.activate.ActivateDDD.application.service.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_usuario.UsuarioAdapter;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Evento;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Organizador;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.EventoCommand;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.repository.EventoCommandRepository;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

@Service
public class OrganizadorServicio {

    @Autowired
    GestionEventoServicio gestionEventoServicio;
    @Autowired
    EventoCommandRepository eventoCommandRepository;
    @Autowired
    EventoAdapter eventoAdapter;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    UsuarioAdapter usuarioAdapter;

    public void crearEvento(int aforoMaximo, int duracion, String nombre, String descripcion, LocalDateTime fecha, Ubicacion ubicacion, TipoEvento tipo, Long idOrganizador, HashSet<Interes> intereses) throws Exception {
        Usuario usuarioOrganizador = usuarioRepository.findById(idOrganizador).orElseThrow(() -> new RuntimeException("Organizador no encontrado"));
        Organizador organizador = new Organizador(usuarioAdapter.mapUsuarioToDomain(usuarioOrganizador));
        if(fecha.isBefore(LocalDateTime.now()))
            throw new RuntimeException("La fecha del evento no puede ser anterior a la fecha actual");
        Evento evento = new Evento(-1L, aforoMaximo, duracion, nombre, descripcion, fecha, ubicacion, tipo, organizador, intereses);
        organizador.crearEvento(evento);

        EventoCommand eventoMapped = eventoAdapter.mapEventoToInfrastructure(evento);
        eventoCommandRepository.save(eventoMapped);
    }

    @Transactional
    public void cancelarEvento(Long idEvento, Long idOrganizador) throws Exception {
        Usuario usuarioOrganizador = usuarioRepository.findById(idOrganizador).orElseThrow(() -> new RuntimeException("Organizador no encontrado"));
        Organizador organizador = new Organizador(usuarioAdapter.mapUsuarioToDomain(usuarioOrganizador));

        ArrayList<Evento> eventosOrganizados = new ArrayList<>();
        for (Evento evento : gestionEventoServicio.obtenerEventos()) {
            if(evento.getIdOrganizador().equals(idOrganizador)) eventosOrganizados.add(evento);
        }
        organizador.setEventosOrganizados(eventosOrganizados);

        if(organizador.cancelarEvento(idEvento)) {
            eventoCommandRepository.save(eventoAdapter.mapEventoToInfrastructure(
                    organizador.getEventosOrganizados().stream()
                            .filter(evento -> evento.getId().equals(idEvento))
                            .findFirst().orElseThrow(() -> new RuntimeException("Evento no encontrado"))));
        }
    }
}
