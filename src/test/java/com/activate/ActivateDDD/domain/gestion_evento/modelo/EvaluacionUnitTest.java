package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class EvaluacionUnitTest {

    private Participante autor;
    private Ubicacion ubicacion;
    private HashSet<Interes> intereses;

    @BeforeEach
    void setUp() throws Exception {
        ubicacion = new Ubicacion(10L, 20L);
        intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);

        Usuario usuario = new Usuario(1L, "Juan", 25, "juan@gmail.com", intereses, ubicacion);
        autor = new Participante(1L, usuario);
    }

    @Test
    void testEvaluacionValida() throws Exception {
        Usuario usuario = new Usuario(1L, "Juan", 25, "juan@gmail.com", intereses, ubicacion);
        Participante autor = new Participante(1L, usuario);

        Long id = 1L;
        String comentario = "Excelente evento";
        int puntuacion = 5;

        //Evaluacion valida
        Evaluacion evaluacion = new Evaluacion(id, comentario, puntuacion, autor);

        //Verificar que la evaluacion se creo correctamente
        assertEquals(id, evaluacion.getId());
        assertEquals(comentario, evaluacion.getComentario());
        assertEquals(puntuacion, evaluacion.getPuntuacion());
        assertEquals(autor, evaluacion.getAutor());
    }

    @Test
    void testPuntuacionInvalidaMenorQueCero() {
        assertThrows(RuntimeException.class, () -> new Evaluacion(1L, "Buen evento", -1, autor));
    }

    @Test
    void testPuntuacionInvalidaMayorQueCinco() {
        assertThrows(RuntimeException.class, () -> new Evaluacion(1L, "Buen evento", 6, autor));
    }

    @Test
    void testComentarioNulo(){
        Long id = 1L;
        String comentarioNulo = null;
        int puntuacion = 5;

        //Verificar que lanza una excepcion al intentar crear una evaluacion con comentario nulo
        assertThrows(RuntimeException.class, () -> new Evaluacion(id, comentarioNulo, puntuacion, autor));
    }

    @Test
    void testComentarioVacio(){
        Long id = 1L;
        String comentarioVacio = "";
        int puntuacion = 5;

        //Verificar que lanza una excepcion al intentar crear una evaluacion con comentario vacio
        assertThrows(RuntimeException.class, () -> new Evaluacion(id, comentarioVacio, puntuacion, autor));
    }

    @Test
    void testPuntuacionInvalida() throws Exception {
        Long id = 1L;
        String comentario = "Excelente evento";
        int puntuacionInvalida = 6;

        //Verificar que lanza una excepcion al intentar crear una evaluacion con puntuacion invalida
        assertThrows(RuntimeException.class, () -> new Evaluacion(id, comentario, puntuacionInvalida, autor));
    }

    @Test
    void testAutorNulo() throws Exception {
        Long id = 1L;
        String comentario = "Excelente evento";
        int puntuacion = 5;

        //Verificar que lanza una excepcion al intentar crear una evaluacion con autor nulo
        assertThrows(RuntimeException.class, () -> new Evaluacion(id, comentario, puntuacion, null));
    }

}
