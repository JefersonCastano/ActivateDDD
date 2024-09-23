package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.GestionEventoServicio;
import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GestionEventoController.class)
class GestionEventoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GestionEventoServicio gestionEventoServicio;

    @Test
    void testActualizarEstado() throws Exception{
        Long idEvento = 1L;
        Estado estado = Estado.ABIERTO;

        //Configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(gestionEventoServicio).actualizarEstado(eq(idEvento), eq(estado));

        //Realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/eventos/1/estado")
                .param("estado", "ABIERTO"))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarTipo() throws Exception{
        Long idEvento = 1L;
        TipoEvento tipo = TipoEvento.PUBLICO;

        //Configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(gestionEventoServicio).actualizarTipo(eq(idEvento), eq(tipo));

        //Realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/eventos/1/tipo")
                .param("tipo", "PUBLICO"))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarAforoMaximo() throws Exception{
        Long idEvento = 1L;
        int aforoMaximo = 100;

        //Configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(gestionEventoServicio).actualizarAforoMaximo(eq(idEvento), eq(aforoMaximo));

        //Realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/eventos/1/aforo")
                .param("aforoMaximo", "100"))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarFecha() throws Exception{
        Long idEvento = 1L;
        LocalDateTime fecha = LocalDateTime.now();

        //Configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(gestionEventoServicio).actualizarFecha(eq(idEvento), eq(fecha));

        //Realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/eventos/1/fecha")
                .param("fecha", fecha.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void testAgregarEvaluacion() throws Exception{
        Long idEvento = 1L;
        String comentario = "Excelente evento";
        int puntuacion = 5;
        Long idParticipante = 1L;

        //Configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(gestionEventoServicio).agregarEvaluacion(eq(idEvento), eq(comentario), eq(puntuacion), eq(idParticipante));

        //Realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/eventos/1/evaluacion")
                .param("comentario", comentario)
                .param("puntuacion", "5")
                .param("idParticipante", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testAgregarParticipante() throws Exception{
        Long idEvento = 1L;
        Long idParticipante = 1L;

        //Configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(gestionEventoServicio).agregarParticipante(eq(idEvento), eq(idParticipante));

        //Realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/eventos/1/participante")
                .param("idParticipante", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarParticipante() throws Exception{
        Long idEvento = 1L;
        Long idParticipante = 1L;

        //Configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(gestionEventoServicio).eliminarParticipante(eq(idEvento), eq(idParticipante));

        //Realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/eventos/1/participante/eliminar")
                .param("idParticipante", "1"))
                .andExpect(status().isOk());
    }
}