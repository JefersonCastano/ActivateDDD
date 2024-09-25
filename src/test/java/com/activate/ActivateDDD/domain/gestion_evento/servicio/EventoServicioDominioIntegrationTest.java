package com.activate.ActivateDDD.domain.gestion_evento.servicio;

import com.activate.ActivateDDD.application.service.gestion_evento.GestionEventoServicio;
import com.activate.ActivateDDD.domain.commons.*;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.*;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.repository.EventoCommandRepository;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.repository.EventoQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventoServicioDominioIntegrationTest {

    private EventoServicioDominio eventoServicioDominio;
    private GestionEventoServicio gestionEventoServicio;
    private Evento evento;
    private Participante participante;
    private Organizador organizador;
    private Usuario usuario;
    private Usuario usuarioOrg;

    @BeforeEach
    void setUp() throws Exception {
        eventoServicioDominio = new EventoServicioDominio();
        gestionEventoServicio = new GestionEventoServicio();
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);
        Ubicacion ubicacion = new Ubicacion(10L, 20L);

        usuario = new Usuario(1L, "Juan", 25, "juan@gmail.com", intereses, ubicacion);
        usuarioOrg= new Usuario(2L, "Org", 25, "org@gmail.com", intereses, ubicacion);
        organizador=new Organizador(usuario);

        evento = new Evento(1L, 100, 120, "Evento de Prueba",
                "Descripción del evento", LocalDateTime.now().plusDays(1),
                ubicacion, TipoEvento.PUBLICO, organizador, intereses);

        participante = new Participante(1L, usuario);
    }

    @Test
    void testAgregarParticipante() {
        boolean resultado = eventoServicioDominio.agregarParticipante(evento, participante);

        assertTrue(resultado, "El participante debería ser agregado exitosamente.");
        assertTrue(evento.getParticipantes().contains(participante), "El evento debería contener al participante.");
    }

    @Test
    void testConvertirEventoAInfo() {
        EventoInfo eventoInfo = eventoServicioDominio.convertirEventoAInfo(evento);

        assertNotNull(eventoInfo, "El objeto EventoInfo no debería ser nulo.");
        assertEquals(evento.getId(), eventoInfo.getId(), "El ID del evento debería coincidir.");
        assertEquals(evento.getNombre(), eventoInfo.getNombre(), "El nombre del evento debería coincidir.");
        // Add more assertions as needed
    }
}