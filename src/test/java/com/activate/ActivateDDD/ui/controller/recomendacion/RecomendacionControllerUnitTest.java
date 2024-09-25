package com.activate.ActivateDDD.ui.controller.recomendacion;

import com.activate.ActivateDDD.application.service.recomendacion.RecomendacionServicio;
import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecomendacionControllerUnitTest {

    @Mock
    private RecomendacionServicio recomendacionServicio;

    @InjectMocks
    private RecomendacionController recomendacionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEmparejar() throws Exception {
        Long idUsuario = 1L;
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.DEPORTE);
        Ubicacion ubicacion = new Ubicacion(40L, -3L);

        EventoInfo eventoInfo = new EventoInfo(
                1L, 100, 120, "Evento Test", "Descripción Test",
                LocalDateTime.now(), ubicacion, Estado.ABIERTO,
                TipoEvento.PUBLICO, "Organizador Test", intereses
        );

        ArrayList<EventoInfo> eventosEsperados = new ArrayList<>();
        eventosEsperados.add(eventoInfo);

        // Simular el comportamiento del servicio
        when(recomendacionServicio.emparejar(idUsuario)).thenReturn(eventosEsperados);
        ArrayList<EventoInfo> eventosObtenidos = recomendacionController.emparejar(idUsuario);

        assertNotNull(eventosObtenidos);
        assertEquals(1, eventosObtenidos.size());
        assertEquals("Evento Test", eventosObtenidos.getFirst().getNombre());
        verify(recomendacionServicio).emparejar(idUsuario);
    }

}