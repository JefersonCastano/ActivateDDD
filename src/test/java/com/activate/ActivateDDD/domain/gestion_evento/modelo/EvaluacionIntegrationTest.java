package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EvaluacionIntegrationTest {

    private Evaluacion evaluacion;
    private Participante autor;
    private Usuario usuario;
    private HashSet<Interes> intereses = new HashSet<>();
    private Ubicacion ubicacion;

    @BeforeEach
    void setUp() throws Exception {
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.POLITICA);
        ubicacion = new Ubicacion(40L, -3L);
        usuario = new Usuario(1L, "Juan", 25, "juan@gmail.com", intereses, ubicacion);
        autor = new Participante(1L, usuario);
        evaluacion = new Evaluacion(1L, "Buen evento", 4, autor);
    }

    @Test
    void testEvaluacionCreation() {
        assertNotNull(evaluacion);
        assertEquals(1L, evaluacion.getId());
        assertEquals("Buen evento", evaluacion.getComentario());
        assertEquals(4, evaluacion.getPuntuacion());
        assertEquals(autor, evaluacion.getAutor());
    }

    @Test
    void testInvalidPuntuacion() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new Evaluacion(2L, "Comentario", 6, autor);
        });
        assertEquals("La puntuación de la evaluación debe estar entre 0 y 5", exception.getMessage());
    }

    @Test
    void testEmptyComentario() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new Evaluacion(3L, "", 3, autor);
        });
        assertEquals("El comentario de la evaluación no puede ser nulo o vacío", exception.getMessage());
    }

    @Test
    void testNullAutor() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new Evaluacion(4L, "Comentario", 3, null);
        });
        assertEquals("El autor de la evaluación no puede ser nulo", exception.getMessage());
    }
}