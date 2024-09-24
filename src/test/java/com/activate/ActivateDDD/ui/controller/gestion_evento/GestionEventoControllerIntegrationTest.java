package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.GestionEventoServicio;
import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class GestionEventoControllerIntegrationTest {

    @Autowired
    private GestionEventoController gestionEventoController;

    @MockBean
    private GestionEventoServicio gestionEventoServicio;

    private Long idEvento;
    private Estado estado;
    private TipoEvento tipo;
    private int aforoMaximo;
    private LocalDateTime fecha;
    private String comentario;
    private int puntuacion;
    private Long idParticipante;

    @BeforeEach
    void setUp() {
        idEvento = 1L;
        estado = Estado.ABIERTO;
        tipo = TipoEvento.PUBLICO;
        aforoMaximo = 100;
        fecha = LocalDateTime.now();
        comentario = "Excelente evento";
        puntuacion = 5;
        idParticipante = 1L;
    }

    @Test
    void testActualizarEstado() {
        gestionEventoController.actualizarEstado(idEvento, estado);
        verify(gestionEventoServicio).actualizarEstado(idEvento, estado);
    }

    @Test
    void testActualizarTipo() {
        gestionEventoController.actualizarTipo(idEvento, tipo);
        verify(gestionEventoServicio).actualizarTipo(idEvento, tipo);
    }

    @Test
    void testActualizarAforoMaximo() {
        gestionEventoController.actualizarAforoMaximo(idEvento, aforoMaximo);
        verify(gestionEventoServicio).actualizarAforoMaximo(idEvento, aforoMaximo);
    }

    @Test
    void testActualizarFecha() {
        gestionEventoController.actualizarFecha(idEvento, fecha);
        verify(gestionEventoServicio).actualizarFecha(idEvento, fecha);
    }

    @Test
    void testAgregarEvaluacion() {
        gestionEventoController.agregarEvaluacion(idEvento, comentario, puntuacion, idParticipante);
        verify(gestionEventoServicio).agregarEvaluacion(idEvento, comentario, puntuacion, idParticipante);
    }

    @Test
    void testAgregarParticipante() {
        gestionEventoController.agregarParticipante(idEvento, idParticipante);
        verify(gestionEventoServicio).agregarParticipante(idEvento, idParticipante);
    }

    @Test
    void testEliminarParticipante() {
        gestionEventoController.eliminarParticipante(idEvento, idParticipante);
        verify(gestionEventoServicio).eliminarParticipante(idEvento, idParticipante);
    }
}