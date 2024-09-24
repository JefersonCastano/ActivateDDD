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

class ParticipanteUnitTest {

    private Usuario usuario;
    private Participante participante;
    private Ubicacion ubicacion;
    private HashSet<Interes> intereses;
    private EventoInfo evento1;
    private EventoInfo evento2;
    private EventoInfo evento3;
    private EventoInfo evento4;
    private EventoInfo evento5;
    private EventoInfo evento6;
    private LocalDateTime fecha;

    @BeforeEach
    void setUp() throws Exception {
        intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);
        ubicacion = new Ubicacion(40L, -3L);
        fecha = LocalDateTime.now().plusDays(1);

        usuario = new Usuario(1L, "Pepe", 20, "pepe@gmail.com", intereses, ubicacion);
        participante = new Participante(1L, usuario);

        evento1 = new EventoInfo(1L, 10, 60, "Evento1", "Descripcion", fecha, ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
        evento2 = new EventoInfo(2L, 10, 60, "Evento2", "Descripcion", fecha.plusMinutes(30), ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
        evento3 = new EventoInfo(3L, 10, 60, "Evento3", "Descripcion", fecha.plusMinutes(61), ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
        evento4 = new EventoInfo(4L, 10, 60, "Evento4", "Descripcion", fecha.minusMinutes(30), ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
        evento5 = new EventoInfo(5L, 10, 30, "Evento5", "Descripcion", fecha.plusMinutes(15), ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
        evento6 = new EventoInfo(6L, 10, 60, "Evento6", "Descripcion", fecha.plusMinutes(120), ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
    }

    @Test
    void testConstructor() {
        assertNotNull(participante);
        assertEquals(1L, participante.getId());
        assertEquals(usuario, participante.getUsuario());
        assertTrue(participante.getEventosParticipados().isEmpty());
    }

    @Test
    void testDisponibleSinEventosParticipados() {
        assertTrue(participante.estaDisponible(evento1));
    }

    @Test
    void testNoDisponiblePorFechaIgual() {
        participante.getEventosParticipados().add(evento1);
        EventoInfo eventoConMismaFecha = new EventoInfo(7L, 10, 60, "Evento7", "Descripcion", evento1.getFecha(), evento1.getUbicacion(), Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", evento1.getIntereses());
        assertFalse(participante.estaDisponible(eventoConMismaFecha));
    }

    @Test
    void testNoDisponiblePorSolapamientoInicio() {
        participante.getEventosParticipados().add(evento1);
        assertFalse(participante.estaDisponible(evento2), "El participante no debería estar disponible debido a solapamiento de eventos.");
    }

    @Test
    void testNoDisponiblePorSolapamientoFin() {
        participante.getEventosParticipados().add(evento1);
        assertFalse(participante.estaDisponible(evento4));
    }

    @Test
    void testNoDisponiblePorEventoDentroDeOtro() {
        participante.getEventosParticipados().add(evento1);
        assertFalse(participante.estaDisponible(evento5));
    }

    @Test
    void testDisponibleSinSolapamiento() {
        participante.getEventosParticipados().add(evento1);
        assertTrue(participante.estaDisponible(evento3), "El participante debería estar disponible ya que no hay solapamiento de eventos.");
    }

    @Test
    void testDisponibleConEventosNoSolapados() {
        participante.getEventosParticipados().add(evento1);
        assertTrue(participante.estaDisponible(evento6));
    }

    @Test
    void testDisponiblePorEventoTerminadoAntesDelNuevo() {
        participante.getEventosParticipados().add(evento1);
        EventoInfo nuevoEvento = new EventoInfo(9L, 10, 60, "Evento9", "Descripcion", fecha.plusMinutes(61), ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
        assertTrue(participante.estaDisponible(nuevoEvento), "El participante debería estar disponible ya que el evento anterior termina antes del inicio del nuevo.");
    }

    @Test
    void testDisponiblePorEventoIniciaDespuesDeFinalizadoElNuevo() {
        participante.getEventosParticipados().add(evento3);
        EventoInfo nuevoEvento = new EventoInfo(10L, 10, 60, "Evento10", "Descripcion", fecha.minusMinutes(120), ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Juan", intereses);
        assertTrue(participante.estaDisponible(nuevoEvento), "El participante debería estar disponible ya que el evento nuevo termina antes de que empiece el existente.");
    }

}