package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.OrganizadorServicio;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganizadorController.class)
class OrganizadorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizadorServicio organizadorServicio;

    @Test
    void testCrearEvento() throws Exception {
        int aforoMaximo = 100;
        int duracion = 2;
        String nombre = "Evento";
        String descripcion = "Evento de prueba";
        LocalDateTime fecha = LocalDateTime.now();
        Double latitud = 40.416500;
        Double longitud = -3.702560;
        TipoEvento tipo = TipoEvento.PUBLICO;
        Long idOrganizador = 1L;
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.VIDEOJUEGOS);

        //Configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(organizadorServicio).crearEvento(eq(1L), eq(aforoMaximo), eq(duracion), eq(nombre), eq(descripcion), eq(fecha), any(Ubicacion.class), eq(tipo), eq(idOrganizador), eq(intereses));

        //Realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/eventos")
                .param("aforoMaximo", "100")
                .param("duracion", "2")
                .param("nombre", "Evento")
                .param("descripcion", "Evento de prueba")
                .param("fecha", LocalDateTime.now().toString())
                .param("latitud", "40.416500")
                .param("longitud", "-3.702560")
                .param("tipo", "PUBLICO")
                .param("idOrganizador", "1")
                .param("intereses", "VIDEOJUEGOS"))
                .andExpect(status().isOk());
    }

    @Test
    void testCancelarEvento() throws Exception {
        Long idEvento = 1L;
        Long idOrganizador = 1L;

        //Configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(organizadorServicio).cancelarEvento(eq(idEvento), eq(idOrganizador));

        //Realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/eventos/cancelar")
                .param("idEvento", "1")
                .param("idOrganizador", "1"))
                .andExpect(status().isOk());
    }
}