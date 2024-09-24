package com.activate.ActivateDDD.domain.recomendacion.modelo;

import com.activate.ActivateDDD.domain.commons.*;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecomendacionIntegrationTest {

    private Recomendacion recomendacion;
    private Usuario usuario;
    private ArrayList<EventoInfo> eventosDisponibles;
    private EventoInfo eventoInfo;

    @BeforeEach
    void setUp() throws Exception {
        recomendacion = new Recomendacion();

        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        Ubicacion ubicacion = new Ubicacion(10L, 20L);

        usuario = new Usuario(1L, "Juan", 25, "juan@gmail.com", intereses, ubicacion);

        eventoInfo = new EventoInfo(1L, 100, 120, "Evento de Prueba",
                "Descripción del evento", LocalDateTime.now().plusDays(1),
                ubicacion, Estado.ABIERTO, TipoEvento.PUBLICO, "Organizador", intereses);

        eventosDisponibles = new ArrayList<>();
        eventosDisponibles.add(eventoInfo);
    }

    @Test
    void testEmparejar() {
        recomendacion.emparejar(usuario, eventosDisponibles);

        assertTrue(recomendacion.getEmparejamientos().containsKey(usuario.getId()), "El usuario debería estar emparejado.");
        assertEquals(1, recomendacion.getEmparejamientos().get(usuario.getId()).size(), "Debería haber un evento emparejado.");
        assertEquals(eventoInfo, recomendacion.getEmparejamientos().get(usuario.getId()).getFirst(), "El evento emparejado debería ser el evento de prueba.");
    }

    @Test
    void testRecomendarEvento() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);

        recomendacion.recomendarEvento(eventoInfo, usuarios);

        assertTrue(recomendacion.getEmparejamientos().containsKey(usuario.getId()), "El usuario debería tener recomendaciones.");
        assertEquals(1, recomendacion.getEmparejamientos().get(usuario.getId()).size(), "Debería haber un evento recomendado.");
        assertEquals(eventoInfo, recomendacion.getEmparejamientos().get(usuario.getId()).getFirst(), "El evento recomendado debería ser el evento de prueba.");
    }
}