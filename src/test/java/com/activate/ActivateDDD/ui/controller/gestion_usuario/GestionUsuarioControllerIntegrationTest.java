package com.activate.ActivateDDD.ui.controller.gestion_usuario;

import com.activate.ActivateDDD.application.service.gestion_usuario.GestionUsuarioServicio;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GestionUsuarioController.class)
class GestionUsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GestionUsuarioServicio gestionUsuarioServicio;

    @Test
    void testCrearUsuario() throws Exception {
        String nombre = "Juan";
        int edad = 20;
        String email = "juan@gmail.com";
        HashSet<Interes> intereses = new HashSet<>();
        intereses.add(Interes.MUSICA);
        Long latitud = 40L;
        Long longitud = -3L;

        //configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(gestionUsuarioServicio).crearUsuario(eq(nombre), eq(edad), eq(email), eq(intereses), any(Ubicacion.class));

        //realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/usuarios")
                .param("nombre", nombre)
                .param("edad", String.valueOf(edad))
                .param("email", email)
                .param("intereses", "MUSICA")
                .param("latitud", String.valueOf(latitud))
                .param("longitud", String.valueOf(longitud)))
                .andExpect(status().isOk()
        );
    }

    @Test
    void testEditarPerfil() throws Exception {
        Long id = 1L;
        String nombre = "Juan";
        int edad = 20;
        String email = "juan@gmail.com";

        //configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(gestionUsuarioServicio).editarPerfil(eq(id), eq(nombre), eq(edad), eq(email));

        //realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/usuarios/1/perfil")
                .param("nombre", nombre)
                .param("edad", String.valueOf(edad))
                .param("email", email))
                .andExpect(status().isOk());
    }

    @Test
    void testAgregarInteres() throws Exception {
        Long id = 1L;
        Interes interes = Interes.MUSICA;

        //configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(gestionUsuarioServicio).agregarInteres(eq(id), eq(interes));

        //realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/usuarios/1/intereses")
                .param("interes", "MUSICA"))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarUbicacion() throws Exception {
        Long id = 1L;
        Ubicacion ubicacion = new Ubicacion(40L, -3L);

        //configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(gestionUsuarioServicio).actualizarUbicacion(eq(id), eq(ubicacion));

        //realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/usuarios/1/ubicacion")
                .param("latitud", "40")
                .param("longitud", "-3"))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarInteres() throws Exception {
        Long id = 1L;
        Interes interes = Interes.MUSICA;

        //configurar el mock para que no haga nada cuando se llame al servicio
        doNothing().when(gestionUsuarioServicio).eliminarInteres(eq(id), eq(interes));

        //realizar la solicitud HTTP simulada y verificar que sea exitosa
        mockMvc.perform(post("/usuarios/1/intereses/eliminar")
                .param("interes", "MUSICA"))
                .andExpect(status().isOk());
    }
}