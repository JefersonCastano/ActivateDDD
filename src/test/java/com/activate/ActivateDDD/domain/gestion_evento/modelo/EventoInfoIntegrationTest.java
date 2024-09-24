package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.commons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventoInfoIntegrationTest {

    private EventoInfo eventoInfo;
    private Ubicacion ubicacion;
    private HashSet<Interes> intereses;

    @BeforeEach
    void setUp() {
        ubicacion = new Ubicacion(40L, -3L);
        intereses = new HashSet<>();
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.CINE);
        intereses.add(Interes.POLITICA);
        eventoInfo = new EventoInfo(1L, 100, 120, "Concierto", "Concierto de rock", LocalDateTime.now().plusDays(1), ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Organizador", intereses);
    }

    @Test
    void testEventoInfoCreation() {
        assertNotNull(eventoInfo);
        assertEquals(1L, eventoInfo.getId());
        assertEquals(100, eventoInfo.getAforoMaximo());
        assertEquals(120, eventoInfo.getDuracion());
        assertEquals("Concierto", eventoInfo.getNombre());
        assertEquals("Concierto de rock", eventoInfo.getDescripcion());
        assertEquals(ubicacion, eventoInfo.getUbicacion());
        assertEquals(Estado.ABIERTO, eventoInfo.getEstado());
        assertEquals(TipoEvento.PUBLICO, eventoInfo.getTipo());
        assertEquals("Organizador", eventoInfo.getNombreOrganizador());
        assertEquals(intereses, eventoInfo.getIntereses());
    }
}