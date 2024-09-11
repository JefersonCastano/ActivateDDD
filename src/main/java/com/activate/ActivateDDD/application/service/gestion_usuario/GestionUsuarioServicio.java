package com.activate.ActivateDDD.application.service.gestion_usuario;

import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class GestionUsuarioServicio {
    public void crearUsuario(String nombre, int edad, String email, HashSet<Interes> intereses, Ubicacion ubicacion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Usuario obtenerUsuario(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Usuario> obtenerUsuarios() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void editarPerfil(Long id, String nombre, int edad, String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void agregarInteres(Long id, Interes interes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actualizarUbicacion(Long id, Ubicacion ubicacion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void eliminarInteres(Long id, Interes interes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
