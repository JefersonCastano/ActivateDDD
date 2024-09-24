package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class OrganizadorUnitTest {
    private Organizador organizador;
    private Usuario usuario;
    private Evento evento;

    @BeforeEach
    void setUp() throws Exception {
        // Configuración común para todas las pruebas
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);
        Ubicacion ubicacion = new Ubicacion(10L, 20L);

        usuario = new Usuario(1L, "Juan", 25, "juan@gmail.com", intereses, ubicacion);

        evento = new Evento(1L, 100, 120, "Evento de Prueba",
                "Descripción del evento", LocalDateTime.now().plusDays(1),
                ubicacion, TipoEvento.PUBLICO, null, intereses);

        organizador = new Organizador(1L, usuario, evento);
    }

    @Test
    void testCrearEvento() {
        // Arrange
        Evento nuevoEvento = new Evento(2L, 50, 90, "Nuevo Evento",
                "Descripción del nuevo evento", LocalDateTime.now().plusDays(2),
                evento.getUbicacion(), TipoEvento.PRIVADO, organizador, evento.getIntereses());

        // Act
        boolean resultado = organizador.crearEvento(nuevoEvento);

        // Assert
        assertTrue(resultado, "El evento debería ser creado exitosamente.");
        assertEquals(2, organizador.getEventosOrganizados().size(), "El organizador debería tener 2 eventos organizados.");
        assertEquals(nuevoEvento, organizador.getEventosOrganizados().get(1), "El evento creado debería ser el nuevo evento.");
    }

    @Test
    void testCancelarEventoExistente() {
        // Act
        boolean resultado = organizador.cancelarEvento(1L);

        // Assert
        assertTrue(resultado, "El evento debería ser cancelado exitosamente.");
        assertEquals(Estado.CANCELADO, evento.getEstado(), "El estado del evento debería ser CANCELADO.");
    }

    @Test
    void testCancelarEventoNoExistente() {
        boolean resultado = organizador.cancelarEvento(2L);

        assertFalse(resultado, "El método debería retornar false cuando el evento no existe.");
    }

    @Test
    void testGetNombre() {
        String nombre = organizador.getNombre();

        assertEquals("Juan", nombre, "El nombre del organizador debería ser Juan.");
    }
}
