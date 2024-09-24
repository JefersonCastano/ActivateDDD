package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.OrganizadorServicio;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrganizadorControllerIntegrationTest {

    @Autowired
    private OrganizadorController organizadorController;

    @MockBean
    private OrganizadorServicio organizadorServicio;

    private int aforoMaximo;
    private int duracion;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private Double latitud;
    private Double longitud;
    private TipoEvento tipo;
    private Long idOrganizador;
    private HashSet<Interes> intereses;
    private Long idEvento;

    @BeforeEach
    void setUp() {
        aforoMaximo = 100;
        duracion = 2;
        nombre = "Evento de prueba";
        descripcion = "Descripción del evento de prueba";
        fecha = LocalDateTime.now();
        latitud = 40.416775;
        longitud = -3.703790;
        tipo = TipoEvento.PUBLICO;
        idOrganizador = 1L;
        intereses = new HashSet<>();
        intereses.add(Interes.MUSICA);
        idEvento = 1L;
    }

    @Test
    void testCrearEvento() {
        organizadorController.crearEvento(aforoMaximo, duracion, nombre, descripcion, fecha, latitud, longitud, tipo, idOrganizador, intereses);
        verify(organizadorServicio).crearEvento(anyLong(), eq(aforoMaximo), eq(duracion), eq(nombre), eq(descripcion), eq(fecha), any(Ubicacion.class), eq(tipo), eq(idOrganizador), eq(intereses));
    }

    @Test
    void testCancelarEvento() {
        organizadorController.cancelarEvento(idEvento, idOrganizador);
        verify(organizadorServicio).cancelarEvento(eq(idEvento), eq(idOrganizador));
    }
}