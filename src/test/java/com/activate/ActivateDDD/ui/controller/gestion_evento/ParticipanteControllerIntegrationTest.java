package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.ParticipanteServicio;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticipanteController.class)
class ParticipanteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ParticipanteServicio participanteServicio;

    @Test
    void testEstaDisponible() throws Exception {
        Long idEvento = 1L;
        Long idParticipante = 1L;

        //Configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(participanteServicio).estaDisponible(eq(idEvento), eq(idParticipante));

        //Realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/participantes/disponibilidad")
                .param("idEvento", "1")
                .param("idParticipante", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerEventosParticipante() throws Exception {
        Long idParticipante = 1L;

        //Configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(participanteServicio).obtenerEventosParticipante(eq(idParticipante));

        //Realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/participantes/eventos")
                .param("idParticipante", "1"))
                .andExpect(status().isOk());
    }
}