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
class EventoIntegrationTest {

    private Evento evento;
    private Ubicacion ubicacion;
    private Organizador organizador;
    private HashSet<Interes> intereses;
    private Usuario usuario;

    @BeforeEach
    void setUp() throws Exception {
        ubicacion = new Ubicacion(40L, -3L);
        organizador = new Organizador( usuario);
        intereses = new HashSet<>();
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.CINE);
        intereses.add(Interes.POLITICA);
        usuario = new Usuario(1L, "Juan", 25, "juan@gmail.com", intereses, ubicacion);
        evento = new Evento(1L, 100, 120, "Concierto", "Concierto de rock", LocalDateTime.now().plusDays(1), ubicacion, TipoEvento.PUBLICO, organizador, intereses);
    }

    @Test
    void testEventoCreation() {
        assertNotNull(evento);
        assertEquals(1L, evento.getId());
        assertEquals(100, evento.getAforoMaximo());
        assertEquals(120, evento.getDuracion());
        assertEquals("Concierto", evento.getNombre());
        assertEquals("Concierto de rock", evento.getDescripcion());
        assertEquals(ubicacion, evento.getUbicacion());
        assertEquals(Estado.ABIERTO, evento.getEstado());
        assertEquals(TipoEvento.PUBLICO, evento.getTipo());
        assertEquals(organizador, evento.getOrganizador());
        assertEquals(intereses, evento.getIntereses());
    }

    @Test
    void testCancelar() {
        evento.cancelar();
        assertEquals(Estado.CANCELADO, evento.getEstado());
    }

    @Test
    void testFinalizar() {
        evento.iniciar();
        evento.finalizar();
        assertEquals(Estado.FINALIZADO, evento.getEstado());
    }

    @Test
    void testIniciar() {
        evento.iniciar();
        assertEquals(Estado.EN_PROCESO, evento.getEstado());
    }

    @Test
    void testCambiarTipo() {
        evento.cambiarTipo();
        assertEquals(TipoEvento.PRIVADO, evento.getTipo());
    }

    @Test
    void testActualizarAforoMaximo() {
        boolean result = evento.actualizarAforoMaximo(150);
        assertTrue(result);
        assertEquals(150, evento.getAforoMaximo());
    }

    @Test
    void testActualizarFecha() {
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(2);
        evento.actualizarFecha(nuevaFecha);
        assertEquals(nuevaFecha, evento.getFecha());
    }

    @Test
    void testAgregarEvaluacion() {
        Participante autor = new Participante(1L, usuario);
        evento.agregarParticipante(autor);
        evento.iniciar();
        evento.finalizar();
        Evaluacion evaluacion = new Evaluacion(1L, "Buen evento", 4, autor);
        evento.agregarEvaluacion(evaluacion);
        assertTrue(evento.getEvaluaciones().contains(evaluacion));
    }

    @Test
    void testAgregarParticipante() {
        Participante participante = new Participante(1L, usuario);
        boolean result = evento.agregarParticipante(participante);
        assertTrue(result);
        assertTrue(evento.getParticipantes().contains(participante));
    }

    @Test
    void testEliminarParticipante() {
        Participante participante = new Participante(1L, usuario);
        evento.agregarParticipante(participante);
        boolean result = evento.eliminarParticipante(participante.getId());
        assertTrue(result);
        assertFalse(evento.getParticipantes().contains(participante));
    }
}