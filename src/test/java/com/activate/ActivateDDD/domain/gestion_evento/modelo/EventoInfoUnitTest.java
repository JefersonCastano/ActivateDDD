package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class EventoInfoUnitTest {

    @Test
    void testConstructor() {
        LocalDateTime fecha = LocalDateTime.now();
        Ubicacion ubicacion = new Ubicacion(10L, 20L);
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.CIENCIA);
        intereses.add(Interes.TECNOLOGIA);
        EventoInfo eventoInfo = new EventoInfo(1L, 100, 60, "Taller de relajacion", "Mejora para la salud mental y fisica", fecha, ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Camilo", intereses);

        assertEquals(1L, eventoInfo.getId());
        assertEquals(100, eventoInfo.getAforoMaximo());
        assertEquals(60, eventoInfo.getDuracion());
        assertEquals("Taller de relajacion", eventoInfo.getNombre());
        assertEquals("Mejora para la salud mental y fisica", eventoInfo.getDescripcion());
        assertEquals(fecha, eventoInfo.getFecha());
        assertEquals(ubicacion, eventoInfo.getUbicacion());
        assertEquals(Estado.ABIERTO, eventoInfo.getEstado());
        assertEquals(TipoEvento.PUBLICO, eventoInfo.getTipo());
        assertEquals("Camilo", eventoInfo.getNombreOrganizador());
        assertEquals(intereses, eventoInfo.getIntereses());
    }

}