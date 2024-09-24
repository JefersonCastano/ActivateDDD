package com.activate.ActivateDDD.ui.controller.gestion_usuario;

import com.activate.ActivateDDD.application.service.gestion_usuario.GestionUsuarioServicio;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GestionUsuarioControllerIntegrationTest {

    @Autowired
    private GestionUsuarioController gestionUsuarioController;

    @MockBean
    private GestionUsuarioServicio gestionUsuarioServicio;

    private String nombre;
    private int edad;
    private String email;
    private HashSet<Interes> intereses;
    private Long latitud;
    private Long longitud;
    private Long id;
    private Ubicacion ubicacion;
    private Usuario usuario;
    private ArrayList<Usuario> usuarios;

    @BeforeEach
    void setUp() throws Exception {
        nombre = "Juan";
        edad = 20;
        email = "juan@gmail.com";
        intereses = new HashSet<>();
        intereses.add(Interes.MUSICA);
        latitud = 40L;
        longitud = -3L;
        id = 1L;
        ubicacion = new Ubicacion(latitud, longitud);
        usuario = new Usuario(id, nombre, edad, email, intereses, ubicacion);
        usuarios = new ArrayList<>();
        usuarios.add(usuario);
    }

    @Test
    void testCrearUsuario() throws Exception {
        gestionUsuarioController.crearUsuario(nombre, edad, email, intereses, latitud, longitud);
        verify(gestionUsuarioServicio).crearUsuario(nombre, edad, email, intereses, ubicacion);
    }

    @Test
    void testObtenerUsuario() {
        when(gestionUsuarioServicio.obtenerUsuario(id)).thenReturn(usuario);
        Usuario result = gestionUsuarioController.obtenerUsuario(id);
        assertNotNull(result);
        assertEquals(usuario, result);
        verify(gestionUsuarioServicio).obtenerUsuario(id);
    }

    @Test
    void testObtenerUsuarios() {
        when(gestionUsuarioServicio.obtenerUsuarios()).thenReturn(usuarios);
        ArrayList<Usuario> result = gestionUsuarioController.obtenerUsuarios();
        assertNotNull(result);
        assertEquals(usuarios, result);
        verify(gestionUsuarioServicio).obtenerUsuarios();
    }

    @Test
    void testEditarPerfil() throws Exception {
        gestionUsuarioController.editarPerfil(id, nombre, edad, email);
        verify(gestionUsuarioServicio).editarPerfil(id, nombre, edad, email);
    }

    @Test
    void testAgregarInteres() {
        gestionUsuarioController.agregarInteres(id, Interes.MUSICA);
        verify(gestionUsuarioServicio).agregarInteres(id, Interes.MUSICA);
    }

    @Test
    void testActualizarUbicacion() throws Exception {
        gestionUsuarioController.actualizarUbicacion(id, ubicacion);
        verify(gestionUsuarioServicio).actualizarUbicacion(id, ubicacion);
    }

    @Test
    void testEliminarInteres() {
        gestionUsuarioController.eliminarInteres(id, Interes.MUSICA);
        verify(gestionUsuarioServicio).eliminarInteres(id, Interes.MUSICA);
    }
}