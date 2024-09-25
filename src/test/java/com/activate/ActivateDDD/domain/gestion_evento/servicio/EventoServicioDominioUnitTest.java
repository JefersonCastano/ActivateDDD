package com.activate.ActivateDDD.domain.gestion_evento.servicio;

import com.activate.ActivateDDD.application.service.gestion_evento.GestionEventoServicio;
import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Evento;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServicioDominioUnitTest {

    @InjectMocks
    private EventoServicioDominio eventoServicioDominio;

    @InjectMocks
    private GestionEventoServicio gestionEventoServicio;

    @Mock
    private Evento evento;

    @Mock
    private Participante participante;

    private EventoInfo eventoInfo;


    @BeforeEach
    void setUp() {
        LocalDateTime fecha = LocalDateTime.now();
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);
        eventoInfo = new EventoInfo(1L, 10, 60, "Evento1", "Descripcion", fecha, new Ubicacion(40L, -3L), Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
    }

    @Test
    void agregarParticipante_participanteDisponible() {
        when(participante.estaDisponible(any(EventoInfo.class))).thenReturn(true);
        when(evento.agregarParticipante(participante)).thenReturn(true);

        boolean result = eventoServicioDominio.agregarParticipante(evento, participante);

        assertTrue(result);
        verify(participante).estaDisponible(any(EventoInfo.class));
        verify(evento).agregarParticipante(participante);
    }

    @Test
    void agregarParticipante_participanteNoDisponible() {
        // Asegurarse de que eventoInfo tenga una fecha válida
        LocalDateTime fecha = LocalDateTime.now();
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);
        eventoInfo = new EventoInfo(1L, 10, 60, "Evento1", "Descripcion", fecha, new Ubicacion(40L, -3L), Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);

        when(participante.estaDisponible(any(EventoInfo.class))).thenReturn(false);
        when(evento.getFecha()).thenReturn(fecha);  // Establecer la fecha en el mock de evento también

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoServicioDominio.agregarParticipante(evento, participante);
        });

        // Verifica que el mensaje de excepción sea correcto con la fecha válida
        assertEquals("El participante ya está inscrito en un evento en la misma fecha y hora: " + eventoInfo.getFecha().toString(), exception.getMessage());

        verify(participante).estaDisponible(any(EventoInfo.class));
        verify(evento, never()).agregarParticipante(participante);
    }

    @Test
    void convertirEventoAInfo() {
        when(evento.getId()).thenReturn(1L);
        when(evento.getAforoMaximo()).thenReturn(10);
        when(evento.getDuracion()).thenReturn(60);
        when(evento.getNombre()).thenReturn("Evento1");
        when(evento.getDescripcion()).thenReturn("Descripcion");
        when(evento.getFecha()).thenReturn(eventoInfo.getFecha());
        when(evento.getUbicacion()).thenReturn(new Ubicacion(40L, -3L));
        when(evento.getEstado()).thenReturn(Estado.ABIERTO);
        when(evento.getTipo()).thenReturn(TipoEvento.PUBLICO);
        when(evento.getNombreOrganizador()).thenReturn("Juan");
        when(evento.getIntereses()).thenReturn(eventoInfo.getIntereses());

        EventoInfo result = eventoServicioDominio.convertirEventoAInfo(evento);

        // Comparar cada campo
        assertEquals(eventoInfo.getId(), result.getId());
        assertEquals(eventoInfo.getAforoMaximo(), result.getAforoMaximo());
        assertEquals(eventoInfo.getDuracion(), result.getDuracion());
        assertEquals(eventoInfo.getNombre(), result.getNombre());
        assertEquals(eventoInfo.getDescripcion(), result.getDescripcion());
        assertEquals(eventoInfo.getFecha(), result.getFecha());
        assertEquals(eventoInfo.getUbicacion(), result.getUbicacion());
        assertEquals(eventoInfo.getEstado(), result.getEstado());
        assertEquals(eventoInfo.getTipo(), result.getTipo());
        assertEquals(eventoInfo.getNombreOrganizador(), result.getNombreOrganizador());
        assertEquals(eventoInfo.getIntereses(), result.getIntereses());
    }

}