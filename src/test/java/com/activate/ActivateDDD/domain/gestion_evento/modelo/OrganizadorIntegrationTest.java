package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.commons.*;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrganizadorIntegrationTest {

    private Organizador organizador;
    private Usuario usuario;
    private Evento evento;

    @BeforeEach
    void setUp() throws Exception {
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);
        Ubicacion ubicacion = new Ubicacion(10L, 20L);

        usuario = new Usuario(1L, "Juan", 25, "juan@gmail.com", intereses, ubicacion);
        organizador = new Organizador(usuario);
        evento = new Evento(1L, 100, 120, "Evento de Prueba",
                "Descripción del evento", LocalDateTime.now().plusDays(1),
                ubicacion, TipoEvento.PUBLICO, organizador, intereses);
        organizador.crearEvento(evento);
    }

    @Test
    void testCrearEvento() {
        Evento nuevoEvento = new Evento(2L, 50, 90, "Nuevo Evento",
                "Descripción del nuevo evento", LocalDateTime.now().plusDays(2),
                evento.getUbicacion(), TipoEvento.PRIVADO, organizador, evento.getIntereses());

        boolean resultado = organizador.crearEvento(nuevoEvento);

        assertTrue(resultado, "El evento debería ser creado exitosamente.");
        assertEquals(2, organizador.getEventosOrganizados().size(), "El organizador debería tener 2 eventos organizados.");
        assertEquals(nuevoEvento, organizador.getEventosOrganizados().get(1), "El evento creado debería ser el nuevo evento.");
    }

    @Test
    void testCancelarEventoExistente() throws Exception {
        boolean resultado = organizador.cancelarEvento(1L);

        assertTrue(resultado, "El evento debería ser cancelado exitosamente.");
        assertEquals(Estado.CANCELADO, evento.getEstado(), "El estado del evento debería ser CANCELADO.");
    }

    @Test
    void testCancelarEventoNoExistente() {
        Exception exception = assertThrows(Exception.class, () -> {
            organizador.cancelarEvento(2L);
        });

        assertEquals("No fue posible cancelar el evento. Evento no encontrado", exception.getMessage());
    }

    @Test
    void testGetNombre() {
        String nombre = organizador.getNombre();

        assertEquals("Juan", nombre, "El nombre del organizador debería ser Juan.");
    }
}