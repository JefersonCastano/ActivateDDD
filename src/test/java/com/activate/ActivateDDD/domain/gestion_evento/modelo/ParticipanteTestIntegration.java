package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.commons.*;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParticipanteIntegrationTest {

    private Participante participante;
    private Usuario usuario;
    private EventoInfo eventoInfo;

    @BeforeEach
    void setUp() throws Exception {
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.TECNOLOGIA);
        Ubicacion ubicacion = new Ubicacion(10L, 20L);

        usuario = new Usuario(1L, "Juan", 25, "juan@gmail.com", intereses, ubicacion);

        eventoInfo = new EventoInfo(1L, 100, 120, "Evento de Prueba",
                "Descripción del evento", LocalDateTime.now().plusDays(1),
                ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Organizador", intereses);

        participante = new Participante(1L, usuario);
    }

    @Test
    void testParticipanteCreation() {
        assertNotNull(participante);
        assertEquals(1L, participante.getId());
        assertEquals(usuario, participante.getUsuario());
        assertTrue(participante.getEventosParticipados().isEmpty());
    }

    @Test
    void testEstaDisponible() {
        assertTrue(participante.estaDisponible(eventoInfo));

        participante.getEventosParticipados().add(eventoInfo);
        assertFalse(participante.estaDisponible(eventoInfo));
    }
}