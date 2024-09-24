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

class EventoUnitTest {

    private Evento evento;
    private Organizador organizador;
    private Usuario usuario;
    private Participante participante;
    private Participante participante2;
    private Evaluacion evaluacion;

    @BeforeEach
    void setUp() throws Exception {
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);
        Ubicacion ubicacion = new Ubicacion(10L, 20L);
        LocalDateTime fecha = LocalDateTime.now().plusDays(1);

        usuario = new Usuario(1L, "Juan", 25, "juan@gmail.com", intereses, ubicacion);
        organizador = new Organizador(1L, usuario, evento);
        participante = new Participante(1L, usuario);
        participante2 = new Participante(2L, new Usuario(2L, "Ana", 22, "ana@gmail.com", intereses, new Ubicacion(30L, 40L)));
        evento = new Evento(1L, 100, 120, "Taller de Yoga", "Evento relajante", fecha, ubicacion, TipoEvento.PUBLICO, organizador, intereses);
        evaluacion = new Evaluacion(1L, "Excelente", 5, participante);
}

    @Test
    void testConstructorEventoValido(){
        assertNotNull(evento);
        assertEquals(100, evento.getAforoMaximo());
        assertEquals(120, evento.getDuracion());
        assertEquals("Taller de Yoga", evento.getNombre());
        assertEquals("Evento relajante", evento.getDescripcion());
        assertEquals(LocalDateTime.now().plusDays(1).toLocalDate(), evento.getFecha().toLocalDate());
        assertEquals(new Ubicacion(10L,20L), evento.getUbicacion());
        assertEquals(TipoEvento.PUBLICO, evento.getTipo());
        // Verificar que los intereses están presentes, sin importar el orden
        assertTrue(evento.getIntereses().contains(Interes.CINE));
        assertTrue(evento.getIntereses().contains(Interes.MUSICA));
        assertTrue(evento.getIntereses().contains(Interes.POLITICA));
    }

    @Test
    void testConstructorEventoDuracionCero(){
        assertThrows(RuntimeException.class, () -> new Evento(1L, 100, 0, "Taller de Yoga", "Evento relajante", LocalDateTime.now().plusDays(1), new Ubicacion(10L, 20L), TipoEvento.PUBLICO, organizador, new HashSet<>()));
    }

    @Test
    void testConstructorEventoDuracionMayorAUnDia(){
        assertThrows(RuntimeException.class, () -> new Evento(1L, 100, 1441, "Taller de Yoga", "Evento relajante", LocalDateTime.now().plusDays(1), new Ubicacion(10L, 20L), TipoEvento.PUBLICO, organizador, new HashSet<>()));
    }

    @Test
    void testConstructorEventoFechaAnterior(){
        assertThrows(RuntimeException.class, () -> new Evento(1L, 100, 120, "Taller de Yoga", "Evento relajante", LocalDateTime.now().minusDays(1), new Ubicacion(10L, 20L), TipoEvento.PUBLICO, organizador, new HashSet<>()));
    }

    @Test
    void testConstructorEventoAforoMaximoCero(){
        assertThrows(RuntimeException.class, () -> new Evento(1L, 0, 120, "Taller de Yoga", "Evento relajante", LocalDateTime.now().plusDays(1), new Ubicacion(10L, 20L), TipoEvento.PUBLICO, organizador, new HashSet<>()));
    }

    @Test
    void testConstructorEventoUbicacionNula(){
        assertThrows(RuntimeException.class, () -> new Evento(1L, 100, 120, "Taller de Yoga", "Evento relajante", LocalDateTime.now().plusDays(1), null, TipoEvento.PUBLICO, organizador, new HashSet<>()));
    }

    @Test
    void testConstructorEventoInteresesNulos(){
        assertThrows(RuntimeException.class, () -> new Evento(1L, 100, 120, "Taller de Yoga", "Evento relajante", LocalDateTime.now().plusDays(1), new Ubicacion(10L, 20L), TipoEvento.PUBLICO, organizador, null));
    }

    @Test
    void testConstructorEventoInteresesVacios(){
        assertThrows(RuntimeException.class, () -> new Evento(1L, 100, 120, "Taller de Yoga", "Evento relajante", LocalDateTime.now().plusDays(1), new Ubicacion(10L, 20L), TipoEvento.PUBLICO, organizador, new HashSet<>()));
    }

    @Test
    void testCancelarEventoFinalizado(){
        evento.setEstado(Estado.EN_PROCESO);
        evento.finalizar();
        assertThrows(RuntimeException.class, () -> evento.cancelar());
    }

    @Test
    void testCancelarEvento(){
        evento.cancelar();
        assertEquals(Estado.CANCELADO, evento.getEstado());
    }

    @Test
    void testCerrarEventoNoAbierto(){
        evento.iniciar();
        assertThrows(RuntimeException.class, () -> evento.cerrar());
    }

    @Test
    void testCerrarEvento(){
        evento.cerrar();
        assertEquals(Estado.CERRADO, evento.getEstado());
    }

    @Test
    void testReabrirEventoNoCerrado(){
        evento.iniciar();
        assertThrows(RuntimeException.class, () -> evento.reabrir());
    }

    @Test
    void testReabrirEvento(){
        evento.cerrar();
        evento.reabrir();
        assertEquals(Estado.ABIERTO, evento.getEstado());
    }

    @Test
    void testFinalizarEventoNoEnProceso(){
        evento.cerrar();
        assertThrows(RuntimeException.class, () -> evento.finalizar());
    }

    @Test
    void testFinalizarEvento(){
        evento.iniciar();
        evento.finalizar();
        assertEquals(Estado.FINALIZADO, evento.getEstado());
    }

    @Test
    void testIniciarEventoNoAbiertoOCerrado(){
        evento.iniciar();
        evento.finalizar();
        assertThrows(RuntimeException.class, () -> evento.iniciar());
    }

    @Test
    void testIniciarEvento(){
        evento.iniciar();
        assertEquals(Estado.EN_PROCESO, evento.getEstado());
    }

    @Test
    void testCambiarTipoEventoPublico(){
        evento.cambiarTipo();
        assertEquals(TipoEvento.PRIVADO, evento.getTipo());
    }

    @Test
    void testCambiarTipoEventoPrivado(){
        evento.cambiarTipo();
        evento.cambiarTipo();
        assertEquals(TipoEvento.PUBLICO, evento.getTipo());
    }

    @Test
    void testCambiarTipoEventoPrivadoAPublico(){
        evento.cambiarTipo();
        evento.cambiarTipo();
        assertEquals(TipoEvento.PUBLICO, evento.getTipo());
    }

    @Test
    void testActualizarAforoMaximoMenorACero(){
        assertThrows(RuntimeException.class, () -> evento.actualizarAforoMaximo(-1));
    }

    @Test
    void testActualizarAforoMaximoEventoFinalizado(){
        evento.setEstado(Estado.FINALIZADO);
        assertThrows(RuntimeException.class, () -> evento.actualizarAforoMaximo(200));
    }

    @Test
    void testActualizarAforoMaximoEventoCancelado(){
        evento.cancelar();
        assertThrows(RuntimeException.class, () -> evento.actualizarAforoMaximo(200));
    }

    @Test
    void testActualizarAforoMaximoEventoSuperado() {
        evento.agregarParticipante(participante);
        assertThrows(RuntimeException.class, () -> evento.actualizarAforoMaximo(0));
    }

    @Test
    void testActualizarAforoMaximo(){
        evento.actualizarAforoMaximo(200);
        assertEquals(200, evento.getAforoMaximo());
    }

    @Test
    void testActualizarAforoMaximoParticipantesSuperanNuevoAforo(){
        evento.agregarParticipante(participante);
        evento.agregarParticipante(participante2);
        assertThrows(RuntimeException.class, () -> evento.actualizarAforoMaximo(1));
    }

    @Test
    void testActualizarFechaEventoFinalizado(){
        evento.setEstado(Estado.FINALIZADO);
        assertThrows(RuntimeException.class, () -> evento.actualizarFecha(LocalDateTime.now().plusDays(2)));
    }

    @Test
    void testActualizarFechaEventoCancelado(){
        evento.cancelar();
        assertThrows(RuntimeException.class, () -> evento.actualizarFecha(LocalDateTime.now().plusDays(2)));
    }

    @Test
    void testActualizarFechaEvento(){
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(2);
        evento.actualizarFecha(nuevaFecha);
        assertEquals(nuevaFecha, evento.getFecha());
    }

    @Test
    void testAgregarParticipanteEventoFinalizado() throws Exception {
        evento.setEstado(Estado.FINALIZADO);
        assertThrows(RuntimeException.class, () -> evento.agregarParticipante(participante));
    }

    @Test
    void testAgregarParticipanteEventoCancelado() throws Exception {
        evento.cancelar();
        assertThrows(RuntimeException.class, () -> evento.agregarParticipante(participante));
    }

    @Test
    void testAgregarParticipanteEvento() throws Exception {
        evento.agregarParticipante(participante);
        assertTrue(evento.getParticipantes().contains(participante));
    }

    @Test
    void testEliminarParticipanteEventoFinalizado() throws Exception {
        evento.setEstado(Estado.FINALIZADO);
        assertThrows(RuntimeException.class, () -> evento.eliminarParticipante(participante));
    }

    @Test
    void testEliminarParticipanteEventoCancelado() throws Exception {
        evento.cancelar();
        assertThrows(RuntimeException.class, () -> evento.eliminarParticipante(participante));
    }

    @Test
    void testAgregarParticipanteYaInscrito() {
        evento.agregarParticipante(participante);
        assertThrows(RuntimeException.class, () -> evento.agregarParticipante(participante), "El participante ya está inscrito en el evento");
    }

    @Test
    void testAgregarParticipanteAforoMaximo() {
        evento.actualizarAforoMaximo(2);
        evento.agregarParticipante(participante);
        evento.agregarParticipante(participante2);
        assertEquals(Estado.CERRADO, evento.getEstado());
    }

    @Test
    void testActualizarAforoMaximoExcedeParticipantes() {
        evento.agregarParticipante(participante);
        assertThrows(RuntimeException.class, () -> evento.actualizarAforoMaximo(0), "No se puede actualizar el aforo de un evento que ya ha superado el nuevo aforo");
    }

    @Test
    void testActualizarAforoMaximoValido() {
        evento.agregarParticipante(participante);
        assertDoesNotThrow(() -> evento.actualizarAforoMaximo(2));
        assertEquals(2, evento.getAforoMaximo());
    }

    @Test
    void testEliminarParticipanteEvento() {
        evento.agregarParticipante(participante);
        evento.eliminarParticipante(participante);
        assertFalse(evento.getParticipantes().contains(participante));
    }

    @Test
    void testEliminarParticipanteEventoNoAbiertoNiCerrado() {
        // Set the event state to a value other than ABIERTO or CERRADO
        evento.setEstado(Estado.CANCELADO);

        // Attempt to remove a participant and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> evento.eliminarParticipante(participante));
        assertEquals("No se puede eliminar un participante de un evento que no está abierto o cerrado", exception.getMessage());
    }

    @Test
    void eliminarParticipante_estadoCerradoDebeEliminar() {
        evento.setEstado(Estado.CERRADO);
        evento.getParticipantes().add(participante);
        assertTrue(evento.eliminarParticipante(participante));
        assertFalse(evento.getParticipantes().contains(participante));
    }

    @Test
    void testEliminarParticipanteNoInscrito() {
        evento.setEstado(Estado.ABIERTO);
        assertThrows(RuntimeException.class, () -> evento.eliminarParticipante(participante), "El participante no está inscrito en el evento");
    }

    @Test
    void testEliminarParticipanteInscrito() {
        evento.setEstado(Estado.ABIERTO);
        evento.agregarParticipante(participante);
        assertDoesNotThrow(() -> evento.eliminarParticipante(participante));
        assertFalse(evento.getParticipantes().contains(participante));
    }

    @Test
    void testAgregarEvaluacionEventoFinalizado() throws Exception {
        evento.setEstado(Estado.FINALIZADO);
        assertThrows(RuntimeException.class, () -> evento.agregarEvaluacion(evaluacion));
    }

    @Test
    void testAgregarEvaluacionParticipanteNoInscrito() throws Exception {
        assertThrows(RuntimeException.class, () -> evento.agregarEvaluacion(evaluacion));
    }

    @Test
    void testAgregarEvaluacionEvento() throws Exception {
        evento.agregarParticipante(participante);
        evento.setEstado(Estado.FINALIZADO);
        evento.agregarEvaluacion(evaluacion);
        assertTrue(evento.getEvaluaciones().contains(evaluacion));
    }

    @Test
    void testAgregarEvaluacionEventoNoFinalizado() throws Exception {
        evento.agregarParticipante(participante);
        assertThrows(RuntimeException.class, () -> evento.agregarEvaluacion(evaluacion));
    }

    @Test
    void testAgregarEvaluacionEventoNoParticipante() throws Exception {
        assertThrows(RuntimeException.class, () -> evento.agregarEvaluacion(evaluacion));
    }

    @Test
    void testActualizarFechaMenosDeDoceHoras() {
        LocalDateTime nuevaFecha = LocalDateTime.now().plusHours(11);
        assertThrows(RuntimeException.class, () -> evento.actualizarFecha(nuevaFecha));
    }

    @Test
    void testActualizarFechaValida() {
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(2);
        evento.actualizarFecha(nuevaFecha);
        assertEquals(nuevaFecha, evento.getFecha());
    }

    @Test
    void testActualizarFechaAnterior() {
        LocalDateTime fechaAnterior = LocalDateTime.now().minusDays(1);
        assertThrows(RuntimeException.class, () -> evento.actualizarFecha(fechaAnterior));
    }

    @Test
    void testNombreOrganizador() {
        assertEquals("Juan", evento.getNombreOrganizador());
    }

    @Test
    void testIniciarEventoEstadoNoAbiertoNiCerrado() {
        evento.setEstado(Estado.CANCELADO);
        assertThrows(RuntimeException.class, () -> evento.iniciar());
    }

    @Test
    void testIniciarEventoEstadoAbierto() {
        evento.setEstado(Estado.ABIERTO);
        assertDoesNotThrow(() -> evento.iniciar());
        assertEquals(Estado.EN_PROCESO, evento.getEstado());
    }

    @Test
    void testIniciarEventoEstadoCerrado() {
        evento.setEstado(Estado.CERRADO);
        assertDoesNotThrow(() -> evento.iniciar());
        assertEquals(Estado.EN_PROCESO, evento.getEstado());
    }

    @Test
    void testActualizarFechaEventoEnProceso() {
        evento.setEstado(Estado.EN_PROCESO);
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(2);
        assertThrows(RuntimeException.class, () -> evento.actualizarFecha(nuevaFecha), "No se puede actualizar la fecha de un evento que ya ha iniciado");
    }

}