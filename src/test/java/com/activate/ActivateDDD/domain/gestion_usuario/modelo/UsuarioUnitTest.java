package com.activate.ActivateDDD.domain.gestion_usuario.modelo;

import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioUnitTest {

    private Usuario usuario;
    private HashSet<Interes> intereses;
    private Ubicacion ubicacion;

    @BeforeEach
    void setUp() throws Exception {
        intereses = new HashSet<>();
        intereses.add(Interes.CINE);
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.DEPORTE);
        ubicacion = new Ubicacion(40L, -3L);
        usuario = new Usuario(1L, "Pepe", 20, "pepe@gmail.com", intereses, ubicacion);
    }

    @Test
    void testConstructor() {
        assertNotNull(usuario);
        assertEquals(1L, usuario.getId());
        assertEquals("Pepe", usuario.getNombre());
        assertEquals(20, usuario.getEdad());
        assertEquals("pepe@gmail.com", usuario.getEmail());
        assertEquals(intereses, usuario.getIntereses());
        assertEquals(ubicacion, usuario.getUbicacion());
    }

    @Test
    void testConstructorInteresesInsuficientes() {
        HashSet<Interes> pocosIntereses = new HashSet<>();
        pocosIntereses.add(Interes.CINE);
        pocosIntereses.add(Interes.MUSICA);
        Exception exception = assertThrows(Exception.class, () -> {
            new Usuario(1L, "Pepe", 20, "pepe@gmail.com", pocosIntereses, ubicacion);
        });
        assertEquals("Numero de intereses insuficiente", exception.getMessage());
    }

    @Test
    void testEditarPerfil() throws Exception {
        assertTrue(usuario.editarPerfil("Juan", 25, "juan@gmail.com"));
        assertEquals("Juan", usuario.getNombre());
        assertEquals(25, usuario.getEdad());
        assertEquals("juan@gmail.com", usuario.getEmail());
    }

    @Test
    void testEditarPerfilThrowsExceptionForNegativeAge() {
        Exception exception = assertThrows(Exception.class, () -> {
            usuario.editarPerfil("Juan", -1, "juan@gmail.com");
        });
        assertEquals("Edad negativa", exception.getMessage());
    }

    @Test
    void testEditarPerfilThrowsExceptionForUnderage() {
        Exception exception = assertThrows(Exception.class, () -> {
            usuario.editarPerfil("Juan", 17, "juan@gmail.com");
        });
        assertEquals("Edad no valida (Debes ser mayor de edad)", exception.getMessage());
    }

    @Test
    void testEditarPerfilThrowsExceptionForNullEmail() {
        Exception exception = assertThrows(Exception.class, () -> {
            usuario.editarPerfil("Juan", 25, null);
        });
        assertEquals("Email no valido", exception.getMessage());
    }

    @Test
    void testEditarPerfilThrowsExceptionForEmptyEmail() {
        Exception exception = assertThrows(Exception.class, () -> {
            usuario.editarPerfil("Juan", 25, "");
        });
        assertEquals("Email no valido", exception.getMessage());
    }

    @Test
    void testEditarPerfilThrowsExceptionForEmailWithoutAtSymbol() {
        Exception exception = assertThrows(Exception.class, () -> {
            usuario.editarPerfil("Juan", 25, "juangmail.com");
        });
        assertEquals("Email no valido", exception.getMessage());
    }

    @Test
    void testEditarPerfilThrowsExceptionForEmailWithoutDomain() {
        Exception exception = assertThrows(Exception.class, () -> {
            usuario.editarPerfil("Juan", 25, "juan@");
        });
        assertEquals("Email no valido", exception.getMessage());
    }

    @Test
    void testEditarPerfilThrowsExceptionForEmailWithInvalidCharacters() {
        Exception exception = assertThrows(Exception.class, () -> {
            usuario.editarPerfil("Juan", 25, "juan@!gmail.com");
        });
        assertEquals("Email no valido", exception.getMessage());
    }

    @Test
    void testAgregarInteres() throws Exception {
        assertTrue(usuario.agregarInteres(Interes.TECNOLOGIA));
        assertTrue(usuario.getIntereses().contains(Interes.TECNOLOGIA));
    }

    @Test
    void testActualizarUbicacion() throws Exception {
        Ubicacion nuevaUbicacion = new Ubicacion(41L, -4L);
        assertTrue(usuario.actualizarUbicacion(nuevaUbicacion));
        assertEquals(nuevaUbicacion, usuario.getUbicacion());
    }

    @Test
    void testActualizarUbicacionThrowsExceptionForSameLocation() {
        Exception exception = assertThrows(Exception.class, () -> {
            usuario.actualizarUbicacion(ubicacion);
        });
        assertEquals("Innecesario actualizar ubicacion", exception.getMessage());
    }

    @Test
    void testActualizarUbicacionWithDifferentLocation() throws Exception {
        Ubicacion nuevaUbicacion = new Ubicacion(41L, -4L);
        assertTrue(usuario.actualizarUbicacion(nuevaUbicacion));
        assertEquals(nuevaUbicacion, usuario.getUbicacion());
    }

}