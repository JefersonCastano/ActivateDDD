package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.OrganizadorServicio;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizadorControllerUnitTest {

    @Mock
    private OrganizadorServicio organizadorServicio;

    @InjectMocks
    private OrganizadorController organizadorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearEvento() {
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

        //Llamada al método a probar
        organizadorController.crearEvento(aforoMaximo, duracion, nombre, descripcion, fecha, latitud, longitud, tipo, idOrganizador, intereses);

        //Verificación de que el método de la capa de aplicación fue invocado
        verify(organizadorServicio).crearEvento(eq(1L), eq(aforoMaximo), eq(duracion), eq(nombre), eq(descripcion), eq(fecha), any(Ubicacion.class), eq(tipo), eq(idOrganizador), eq(intereses));
    }

    @Test
    void testCancelarEvento() {
        Long idEvento = 1L;
        Long idOrganizador = 1L;

        //Llamada al método a probar
        organizadorController.cancelarEvento(idEvento, idOrganizador);

        //Verificación de que el método de la capa de aplicación fue invocado
        verify(organizadorServicio).cancelarEvento(eq(idEvento), eq(idOrganizador));
    }
}