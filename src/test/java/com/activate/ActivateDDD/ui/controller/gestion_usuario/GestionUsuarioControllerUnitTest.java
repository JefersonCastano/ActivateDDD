package com.activate.ActivateDDD.ui.controller.gestion_usuario;

import com.activate.ActivateDDD.application.service.gestion_usuario.GestionUsuarioServicio;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GestionUsuarioControllerUnitTest {

    @Mock
    private GestionUsuarioServicio gestionUsuarioServicio;

    @InjectMocks
    private GestionUsuarioController gestionUsuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuario() throws Exception {
        String nombre = "Juan";
        int edad = 20;
        String email = "juan@gmail.com";
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.MUSICA);
        Long latitud = 40L;
        Long longitud = -3L;

        //Llamada al método a probar
        gestionUsuarioController.crearUsuario(nombre, edad, email, intereses, latitud, longitud);

        //Verificación de que el método de la capa de aplicación fue invocado
        verify(gestionUsuarioServicio).crearUsuario(eq(nombre), eq(edad), eq(email), eq(intereses), any(Ubicacion.class));
    }

    @Test
    void testObtenerUsuario() throws Exception {
        Long id = 1L;
        // Crear un conjunto de intereses
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.MUSICA);
        intereses.add(Interes.CINE);
        intereses.add(Interes.POLITICA);

        // Crear la ubicación
        Ubicacion ubicacion = new Ubicacion(40L, -3L);

        // Crear el objeto Usuario simulado
        Usuario usuario = new Usuario(id, "Juan", 20, "juan@gmail.com", intereses, ubicacion);

        //Simular el comportamiento del servicio
        when(gestionUsuarioServicio.obtenerUsuario(id)).thenReturn(usuario);

        //Llamar al metodo del controlador
        Usuario usuarioObtenido = gestionUsuarioController.obtenerUsuario(id);

        //Verificaciones
        assertNotNull(usuarioObtenido);
        assertEquals("Juan", usuarioObtenido.getNombre());
        assertEquals(20, usuarioObtenido.getEdad());

        //Verificar que el método de la capa de aplicación fue invocado
        verify(gestionUsuarioServicio).obtenerUsuario(eq(id));
    }

    @Test
    void testObtenerUsuarios() throws Exception {
        // Crear conjuntos de intereses
        HashSet<Interes> interesesJuan = new HashSet<>();
        interesesJuan.add(Interes.MUSICA);
        interesesJuan.add(Interes.DEPORTE);
        interesesJuan.add(Interes.CINE);

        HashSet<Interes> interesesMaria = new HashSet<>();
        interesesMaria.add(Interes.TECNOLOGIA);
        interesesMaria.add(Interes.GASTRONOMIA);
        interesesMaria.add(Interes.POLITICA);

        // Crear la lista de usuarios con suficientes intereses
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario(1L, "Juan", 20, "juan@gmail.com", interesesJuan, new Ubicacion(40L, -3L)));
        usuarios.add(new Usuario(2L, "Maria", 25, "maria@gmail.com", interesesMaria, new Ubicacion(40L, -3L)));

        // Simular el comportamiento del servicio
        when(gestionUsuarioServicio.obtenerUsuarios()).thenReturn(usuarios);

        // Llamar al método del controlador
        ArrayList<Usuario> usuariosObtenidos = gestionUsuarioController.obtenerUsuarios();

        // Verificaciones
        assertNotNull(usuariosObtenidos);
        assertEquals(2, usuariosObtenidos.size());
        assertEquals("Juan", usuariosObtenidos.getFirst().getNombre());

        // Verificar que el método de la capa de aplicación fue invocado
        verify(gestionUsuarioServicio).obtenerUsuarios();
    }

    @Test
    void testEditarPerfil() throws Exception {
        Long id = 1L;
        String nombre = "Juan";
        int edad = 20;
        String email = "juan@gmail.com";

        // Llamada al método a probar
        gestionUsuarioController.editarPerfil(id, nombre, edad, email);

        // Verificación de que el método de la capa de aplicación fue invocado
        verify(gestionUsuarioServicio).editarPerfil(eq(id), eq(nombre), eq(edad), eq(email));
    }

    @Test
    void testAgregarInteres() {
        Long id = 1L;
        Interes interes = Interes.MUSICA;

        // Llamada al método a probar
        gestionUsuarioController.agregarInteres(id, interes);

        // Verificación de que el método de la capa de aplicación fue invocado
        verify(gestionUsuarioServicio).agregarInteres(eq(id), eq(interes));
    }

    @Test
    void actualizarUbicacion() throws Exception {
        Long id = 1L;
        Ubicacion ubicacion = new Ubicacion(40L, -3L);

        // Llamada al método a probar
        gestionUsuarioController.actualizarUbicacion(id, ubicacion);

        // Verificación de que el método de la capa de aplicación fue invocado
        verify(gestionUsuarioServicio).actualizarUbicacion(eq(id), eq(ubicacion));
    }

    @Test
    void eliminarInteres() {
        Long id = 1L;
        Interes interes = Interes.MUSICA;

        // Llamada al método a probar
        gestionUsuarioController.eliminarInteres(id, interes);

        // Verificación de que el método de la capa de aplicación fue invocado
        verify(gestionUsuarioServicio).eliminarInteres(eq(id), eq(interes));
    }
}