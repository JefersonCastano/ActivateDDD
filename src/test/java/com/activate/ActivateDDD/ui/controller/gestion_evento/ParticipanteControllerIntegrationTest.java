package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.ParticipanteServicio;
import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class ParticipanteControllerIntegrationTest {

    @Autowired
    private ParticipanteController participanteController;

    @Mock
    private ParticipanteServicio participanteServicio;

    private Long idEvento;
    private Long idParticipante;
    private ArrayList<EventoInfo> eventos;
    private LocalDateTime fecha;
    private Ubicacion ubicacion;
    private HashSet<Interes> intereses;

    @BeforeEach
    void setUp() {
        idEvento = 1L;
        idParticipante = 1L;
        fecha = LocalDateTime.now();
        ubicacion = new Ubicacion(40L, -3L);
        intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);
        eventos = new ArrayList<>();
        eventos.add(new EventoInfo(1L, 10, 60, "Evento1", "Descripcion", fecha, ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses));
    }

    @Test
    void testEstaDisponible() {
        when(participanteServicio.estaDisponible(idEvento, idParticipante)).thenReturn(true);

        boolean disponible = participanteController.estaDisponible(idEvento, idParticipante);

        assertTrue(disponible);
        verify(participanteServicio).estaDisponible(idEvento, idParticipante);
    }

    @Test
    void testObtenerEventosParticipante() {
        when(participanteServicio.obtenerEventosParticipante(idParticipante)).thenReturn(eventos);

        ArrayList<EventoInfo> resultado = participanteController.obtenerEventosParticipante(idParticipante);

        assertEquals(eventos, resultado);
        verify(participanteServicio).obtenerEventosParticipante(idParticipante);
    }
}