package com.activate.ActivateDDD.ui.controller.recomendacion;

import com.activate.ActivateDDD.application.service.recomendacion.RecomendacionServicio;
import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecomendacionController.class)
class RecomendacionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecomendacionServicio recomendacionServicio;

    @Test
    void testEmparejar() throws Exception {
        // Datos de prueba
        Long idUsuario = 1L;
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.DEPORTE);
        Ubicacion ubicacion = new Ubicacion(40L, -3L);

        EventoInfo eventoInfo = new EventoInfo(
                1L, 100, 120, "Evento Test", "Descripción Test",
                LocalDateTime.now(), ubicacion, Estado.ABIERTO,
                TipoEvento.PUBLICO, "Organizador Test", intereses
        );

        ArrayList<EventoInfo> eventos = new ArrayList<>();
        eventos.add(eventoInfo);

        //configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(recomendacionServicio).emparejar(eq(idUsuario));

        // Realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/recomendaciones")
                .param("idUsuario", String.valueOf(idUsuario)))
                .andExpect(status().isOk()
        );
    }

}