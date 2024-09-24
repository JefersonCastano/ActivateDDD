package com.activate.ActivateDDD.domain.gestion_usuario.modelo;

import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioIntegrationTest {

    private Usuario usuario;
    private HashSet<Interes> intereses;
    private Ubicacion ubicacion;

    @BeforeEach
    void setUp() throws Exception {
        intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.DEPORTE);
        ubicacion = new Ubicacion(10L, 20L);

        usuario = new Usuario(1L, "Juan", 25, "juan@gmail.com", intereses, ubicacion);
    }

    @Test
    void testUsuarioCreation() {
        assertNotNull(usuario);
        assertEquals(1L, usuario.getId());
        assertEquals("Juan", usuario.getNombre());
        assertEquals(25, usuario.getEdad());
        assertEquals("juan@gmail.com", usuario.getEmail());
        assertEquals(intereses, usuario.getIntereses());
        assertEquals(ubicacion, usuario.getUbicacion());
    }

    @Test
    void testEditarPerfil() throws Exception {
        boolean result = usuario.editarPerfil("Pedro", 30, "pedro@gmail.com");
        assertTrue(result);
        assertEquals("Pedro", usuario.getNombre());
        assertEquals(30, usuario.getEdad());
        assertEquals("pedro@gmail.com", usuario.getEmail());
    }

    @Test
    void testAgregarInteres() {
        boolean result = usuario.agregarInteres(Interes.TECNOLOGIA);
        assertTrue(result);
        assertTrue(usuario.getIntereses().contains(Interes.TECNOLOGIA));
    }

    @Test
    void testActualizarUbicacion() throws Exception {
        Ubicacion nuevaUbicacion = new Ubicacion(30L, 40L);
        boolean result = usuario.actualizarUbicacion(nuevaUbicacion);
        assertTrue(result);
        assertEquals(nuevaUbicacion, usuario.getUbicacion());
    }
}