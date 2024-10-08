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
    void testObtenerEventosParticipante() throws Exception {
        Long idParticipante = 1L;

        participanteController.obtenerEventosParticipante(idParticipante);
        verify(participanteServicio).obtenerEventosParticipante(eq(idParticipante));
    }
}