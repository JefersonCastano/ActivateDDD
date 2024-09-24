package com.activate.ActivateDDD.domain.gestion_evento.servicio;

import com.activate.ActivateDDD.domain.commons.*;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.*;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventoServicioDominioIntegrationTest {

    private EventoServicioDominio eventoServicioDominio;
    private Evento evento;
    private Participante participante;
    private Usuario usuario;

    @BeforeEach
    void setUp() throws Exception {
        eventoServicioDominio = new EventoServicioDominio();

        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);
        Ubicacion ubicacion = new Ubicacion(10L, 20L);

        usuario = new Usuario(1L, "Juan", 25, "juan@gmail.com", intereses, ubicacion);

        evento = new Evento(1L, 100, 120, "Evento de Prueba",
                "Descripción del evento", LocalDateTime.now().plusDays(1),
                ubicacion, TipoEvento.PUBLICO, null, intereses);

        participante = new Participante(1L, usuario);
    }

    @Test
    void testAgregarParticipante() {
        boolean resultado = eventoServicioDominio.agregarParticipante(evento, participante);

        assertTrue(resultado, "El participante debería ser agregado exitosamente.");
        assertTrue(evento.getParticipantes().contains(participante), "El evento debería contener al participante.");
    }

    @Test
    void testEliminarParticipante() {
        eventoServicioDominio.agregarParticipante(evento, participante);
        boolean resultado = eventoServicioDominio.eliminarParticipante(evento, participante);

        assertTrue(resultado, "El participante debería ser eliminado exitosamente.");
        assertFalse(evento.getParticipantes().contains(participante), "El evento no debería contener al participante.");
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