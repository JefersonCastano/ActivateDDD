package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.GestionEventoServicio;
import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionEventoControllerUnitTest {

    @Mock
    private GestionEventoServicio gestionEventoServicio;

    @InjectMocks
    private GestionEventoController gestionEventoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testActualizarEstado() {
        Long idEvento = 1L;
        Estado estado = Estado.ABIERTO;
        gestionEventoController.actualizarEstado(idEvento, estado);
        verify(gestionEventoServicio).actualizarEstado(eq(idEvento), eq(estado));
    }

    @Test
    void testActualizarTipo() {
        Long idEvento = 1L;
        TipoEvento tipo = TipoEvento.PUBLICO;

        gestionEventoController.actualizarTipo(idEvento, tipo);

        verify(gestionEventoServicio).actualizarTipo(eq(idEvento), eq(tipo));
    }

    @Test
    void testActualizarAforoMaximo() {
        Long idEvento = 1L;
        int aforoMaximo = 100;

        gestionEventoController.actualizarAforoMaximo(idEvento, aforoMaximo);
        verify(gestionEventoServicio).actualizarAforoMaximo(eq(idEvento), eq(aforoMaximo));
    }

    @Test
    void testActualizarFecha() {
        Long idEvento = 1L;
        LocalDateTime fecha = LocalDateTime.now();

        gestionEventoController.actualizarFecha(idEvento, fecha);
        verify(gestionEventoServicio).actualizarFecha(eq(idEvento), eq(fecha));
    }

    @Test
    void testAgregarEvaluacion() {
        Long idEvento = 1L;
        String comentario = "Excelente evento";
        int puntuacion = 5;
        Long idParticipante = 1L;

        gestionEventoController.agregarEvaluacion(idEvento, comentario, puntuacion, idParticipante);
        verify(gestionEventoServicio).agregarEvaluacion(eq(idEvento), eq(comentario), eq(puntuacion), eq(idParticipante));
    }

    @Test
    void testAgregarParticipante() {
        Long idEvento = 1L;
        Long idParticipante = 1L;

        gestionEventoController.agregarParticipante(idEvento, idParticipante);
        verify(gestionEventoServicio).agregarParticipante(eq(idEvento), eq(idParticipante));
    }

    @Test
    void testEliminarParticipante() {
        Long idEvento = 1L;
        Long idParticipante = 1L;

        gestionEventoController.eliminarParticipante(idEvento, idParticipante);
        verify(gestionEventoServicio).eliminarParticipante(eq(idEvento), eq(idParticipante));
    }
}