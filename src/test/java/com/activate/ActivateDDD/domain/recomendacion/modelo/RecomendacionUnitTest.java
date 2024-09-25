package com.activate.ActivateDDD.domain.recomendacion.modelo;

import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RecomendacionUnitTest {

    private Recomendacion recomendacion;
    private Usuario usuario;
    private ArrayList<EventoInfo> eventosDisponibles;
    private Ubicacion ubicacionUsuario;
    private Ubicacion ubicacionEvento;
    private LocalDateTime fecha;
    private HashSet<Interes> intereses;
    private EventoInfo evento;

    @BeforeEach
    void setUp() throws Exception {
        recomendacion = new Recomendacion();
        intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);
        fecha = LocalDateTime.now().plusDays(1);
        ubicacionUsuario = new Ubicacion(40L, -3L);
        usuario = new Usuario(1L, "Pepe", 20, "pepe@gmail.com", intereses, ubicacionUsuario);

        eventosDisponibles = new ArrayList<>();
        ubicacionEvento = new Ubicacion(41L, -4L);
        evento = new EventoInfo(1L, 10, 60, "Concierto", "Descripcion", fecha, ubicacionEvento, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
        eventosDisponibles.add(evento);
    }

    @Test
    void testConstructor() {
        assertNotNull(recomendacion);
        assertTrue(recomendacion.getEmparejamientos().isEmpty());
    }

    @Test
    void testEmparejar() {
        recomendacion.emparejar(usuario, eventosDisponibles);
        assertFalse(recomendacion.getEmparejamientos().isEmpty());
        assertTrue(recomendacion.getEmparejamientos().containsKey(usuario.getId()));
        assertEquals(1, recomendacion.getEmparejamientos().get(usuario.getId()).size());
    }

    @Test
    void testEmparejarThrowsExceptionForNoMatchingEvents() {
        eventosDisponibles.getFirst().getIntereses().clear();
        assertThrows(RuntimeException.class, () -> recomendacion.emparejar(usuario, eventosDisponibles));
    }

    @Test
    void testEmparejarWithMatchingInteresAndCercania() {
        recomendacion.emparejar(usuario, eventosDisponibles);
        assertTrue(recomendacion.getEmparejamientos().containsKey(usuario.getId()));
        assertEquals(1, recomendacion.getEmparejamientos().get(usuario.getId()).size());
    }

    @Test
    void testEmparejarWithNoMatchingInteres() {
        eventosDisponibles.get(0).getIntereses().clear();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            recomendacion.emparejar(usuario, eventosDisponibles);
        });
        assertEquals("No hay eventos recomendables para el usuario", exception.getMessage());
    }

    @Test
    void testEmparejarWithNoCercania() {
        ubicacionEvento = new Ubicacion(100L, 100L);
        EventoInfo eventoLejano = new EventoInfo(2L, 50, 50, "Concierto", "Descripcion", fecha, ubicacionEvento, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
        eventosDisponibles.clear();
        eventosDisponibles.add(eventoLejano);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            recomendacion.emparejar(usuario, eventosDisponibles);
        });
        assertEquals("No hay eventos recomendables para el usuario", exception.getMessage());
    }

    @Test
    void testEmparejarWithExistingEmparejamientos() {
        recomendacion.emparejar(usuario, eventosDisponibles);
        assertTrue(recomendacion.getEmparejamientos().containsKey(usuario.getId()));
        assertEquals(1, recomendacion.getEmparejamientos().get(usuario.getId()).size());

        // Add another event and call emparejar again
        EventoInfo nuevoEvento = new EventoInfo(2L, 30, 30, "Cine", "Descripcion", fecha, ubicacionEvento, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
        eventosDisponibles.add(nuevoEvento);
        recomendacion.emparejar(usuario, eventosDisponibles);

        assertTrue(recomendacion.getEmparejamientos().containsKey(usuario.getId()));
        assertEquals(2, recomendacion.getEmparejamientos().get(usuario.getId()).size());
    }

    @Test
    void testEmparejarWithNoMatchingEvents() {
        // Create an event that does not match the user's interests and is far away
        HashSet<Interes> noMatchingIntereses = new HashSet<>();
        noMatchingIntereses.add(Interes.DEPORTE); // An interest not in the user's interests
        Ubicacion ubicacionLejana = new Ubicacion(100L, 100L); // A far location
        EventoInfo eventoNoRecomendable = new EventoInfo(2L, 50, 50, "Deporte", "Descripcion", fecha, ubicacionLejana, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", noMatchingIntereses);

        eventosDisponibles.clear();
        eventosDisponibles.add(eventoNoRecomendable);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            recomendacion.emparejar(usuario, eventosDisponibles);
        });
        assertEquals("No hay eventos recomendables para el usuario", exception.getMessage());
    }

    @Test
    void testRecomendarEvento() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        recomendacion.recomendarEvento(evento, usuarios);
        assertFalse(recomendacion.getEmparejamientos().isEmpty());
        assertTrue(recomendacion.getEmparejamientos().containsKey(usuario.getId()));
        assertEquals(1, recomendacion.getEmparejamientos().get(usuario.getId()).size());
    }

    @Test
    void testRecomendarEventoWithNoMatchingInteres() {
        HashSet<Interes> noMatchingIntereses = new HashSet<>();
        noMatchingIntereses.add(Interes.DEPORTE); // An interest not in the user's interests
        Ubicacion ubicacionLejana = new Ubicacion(100L, 100L); // A far location
        EventoInfo eventoNoRecomendable = new EventoInfo(2L, 50, 50, "Deporte", "Descripcion", fecha, ubicacionLejana, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", noMatchingIntereses);

        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        recomendacion.recomendarEvento(eventoNoRecomendable, usuarios);
        assertTrue(recomendacion.getEmparejamientos().isEmpty());
    }

    @Test
    void testRecomendarEventoWithNoCercania() {
        ubicacionEvento = new Ubicacion(100L, 100L); // Set a far location
        EventoInfo eventoLejano = new EventoInfo(2L, 50, 50, "Concierto", "Descripcion", fecha, ubicacionEvento, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        recomendacion.recomendarEvento(eventoLejano, usuarios);
        assertTrue(recomendacion.getEmparejamientos().isEmpty());
    }

    @Test
    void testRecomendarEventoWithExistingEmparejamientos() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        recomendacion.recomendarEvento(evento, usuarios); // First call to add events
        assertTrue(recomendacion.getEmparejamientos().containsKey(usuario.getId()));
        assertEquals(1, recomendacion.getEmparejamientos().get(usuario.getId()).size());

        // Add another event and call recomendarEvento again
        EventoInfo nuevoEvento = new EventoInfo(2L, 30, 30, "Cine", "Descripcion", fecha, ubicacionEvento, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
        recomendacion.recomendarEvento(nuevoEvento, usuarios);

        assertTrue(recomendacion.getEmparejamientos().containsKey(usuario.getId()));
        assertEquals(2, recomendacion.getEmparejamientos().get(usuario.getId()).size());
    }

    @Test
    void testRecomendarEventoWithNoMatchingEvents() {
        // Create an event that does not match the user's interests and is far away
        HashSet<Interes> noMatchingIntereses = new HashSet<>();
        noMatchingIntereses.add(Interes.DEPORTE); // An interest not in the user's interests
        Ubicacion ubicacionLejana = new Ubicacion(100L, 100L); // A far location
        EventoInfo eventoNoRecomendable = new EventoInfo(2L, 50, 50, "Deporte", "Descripcion", fecha, ubicacionLejana, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", noMatchingIntereses);

        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        recomendacion.recomendarEvento(eventoNoRecomendable, usuarios);
        assertTrue(recomendacion.getEmparejamientos().isEmpty());
    }
}