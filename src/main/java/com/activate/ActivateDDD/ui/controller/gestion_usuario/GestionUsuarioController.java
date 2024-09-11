package com.activate.ActivateDDD.ui.controller.gestion_usuario;

import com.activate.ActivateDDD.application.service.gestion_usuario.GestionUsuarioServicio;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;

@RestController
public class GestionUsuarioController {
    /**
     * Objeto de la capa de aplicación
     */
    @Autowired
    private GestionUsuarioServicio gestionUsuarioServicio;

    /**
     * Crea un nuevo usuario y lo guarda en el repositorio.
     *
     * @param nombre     Nombre del usuario.
     * @param edad       Edad del usuario.
     * @param email      Correo electrónico del usuario.
     * @param intereses  Intereses del usuario.
     * @param latitud    Latitud de la ubicación del usuario.
     * @param longitud   Longitud de la ubicación del usuario.
     */
    public void crearUsuario(String nombre, int edad, String email, HashSet<Interes> intereses, Long latitud, Long longitud) throws Exception {
        Ubicacion ubicacion = new Ubicacion(latitud, longitud);
        gestionUsuarioServicio.crearUsuario(nombre, edad, email, intereses, ubicacion);
        System.out.println("Usuario creado exitosamente.");
    }

    public Usuario obtenerUsuario(Long id) {
        return gestionUsuarioServicio.obtenerUsuario(id);
    }

    public ArrayList<Usuario> obtenerUsuarios() {
        return gestionUsuarioServicio.obtenerUsuarios();
    }

    public void editarPerfil(Long id, String nombre, int edad, String email) throws Exception {
        gestionUsuarioServicio.editarPerfil(id, nombre, edad, email);
        System.out.println("Perfil editado exitosamente.");
    }

    public void agregarInteres(Long id, Interes interes) {
        gestionUsuarioServicio.agregarInteres(id, interes);
        System.out.println("Interes agregado exitosamente.");
    }

    public void actualizarUbicacion(Long id, Ubicacion ubicacion) throws Exception {
        gestionUsuarioServicio.actualizarUbicacion(id, ubicacion);
        System.out.println("Ubicacion actualizada exitosamente.");
    }

    public void eliminarInteres(Long id, Interes interes) {
        gestionUsuarioServicio.eliminarInteres(id, interes);
        System.out.println("Interes eliminado exitosamente.");
    }
}
