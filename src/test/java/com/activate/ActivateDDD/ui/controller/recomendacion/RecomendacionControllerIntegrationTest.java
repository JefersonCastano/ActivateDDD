package com.activate.ActivateDDD.ui.controller.recomendacion;

import com.activate.ActivateDDD.application.service.recomendacion.RecomendacionServicio;
import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RecomendacionControllerIntegrationTest {

    @Autowired
    private RecomendacionController recomendacionController;

    @MockBean
    private RecomendacionServicio recomendacionServicio;

    private Long idUsuario;
    private ArrayList<EventoInfo> eventos;
    private LocalDateTime fecha;
    private Ubicacion ubicacion;
    private HashSet<Interes> intereses;

    @BeforeEach
    void setUp() {
        idUsuario = 1L;
        eventos = new ArrayList<>();
        fecha = LocalDateTime.now();
        ubicacion = new Ubicacion(40L, -3L);
        intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);

        eventos.add(new EventoInfo(1L, 10, 60, "Evento1", "Descripcion", fecha, ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses));
    }

    @Test
    void testEmparejar() throws Exception {
        when(recomendacionServicio.emparejar(idUsuario)).thenReturn(eventos);

        ArrayList<EventoInfo> resultado = recomendacionController.emparejar(idUsuario);

        assertNotNull(resultado);
        assertEquals(eventos, resultado);
        verify(recomendacionServicio).emparejar(idUsuario);
    }
}