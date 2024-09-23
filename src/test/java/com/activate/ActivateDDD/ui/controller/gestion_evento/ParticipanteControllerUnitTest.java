package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.ParticipanteServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ParticipanteControllerUnitTest {

    @Mock
    private ParticipanteServicio participanteServicio;

    @InjectMocks
    private ParticipanteController participanteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEstaDisponible() {
        Long idEvento = 1L;
        Long idParticipante = 1L;

        //Llamada al método a probar
        participanteController.estaDisponible(idEvento, idParticipante);

        //Verificación de que el método de la capa de aplicación fue invocado
        verify(participanteServicio).estaDisponible(eq(idEvento), eq(idParticipante));
    }

    @Test
    void testObtenerEventosParticipante() {
        Long idParticipante = 1L;

        //Llamada al método a probar
        participanteController.obtenerEventosParticipante(idParticipante);

        //Verificación de que el método de la capa de aplicación fue invocado
        verify(participanteServicio).obtenerEventosParticipante(eq(idParticipante));
    }
}