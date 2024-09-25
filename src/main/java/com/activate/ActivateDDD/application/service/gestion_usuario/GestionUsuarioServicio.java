package com.activate.ActivateDDD.application.service.gestion_usuario;

import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class GestionUsuarioServicio {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    UsuarioAdapter usuarioAdapter;

    public void crearUsuario(String nombre, int edad, String email, HashSet<Interes> intereses, Ubicacion ubicacion) throws Exception {
        Usuario usuario = new Usuario(-1L, nombre, edad, email, intereses, ubicacion);
        com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario usuarioMapped = usuarioAdapter.mapUsuarioToInfrastructure(usuario);
        usuarioRepository.save(usuarioMapped);
    }

    public Usuario obtenerUsuario(Long id) throws Exception {
        com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new Exception("Usuario no encontrado"));
        return usuarioAdapter.mapUsuarioToDomain(usuario);
    }

    public ArrayList<Usuario> obtenerUsuarios() throws Exception {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        for (com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario usuario : usuarioRepository.findAll()) {
            usuarios.add(usuarioAdapter.mapUsuarioToDomain(usuario));
        }
        return usuarios;
    }

    public void editarPerfil(Long id, String nombre, int edad, String email) throws Exception {
        Usuario usuario = obtenerUsuario(id);
        usuario.editarPerfil(nombre, edad, email);
        usuarioRepository.save(usuarioAdapter.mapUsuarioToInfrastructure(usuario));
    }

    public void agregarInteres(Long id, Interes interes) throws Exception {
        Usuario usuario = obtenerUsuario(id);
        usuario.agregarInteres(interes);
        usuarioRepository.save(usuarioAdapter.mapUsuarioToInfrastructure(usuario));
    }

    public void actualizarUbicacion(Long id, Ubicacion ubicacion) throws Exception {
        Usuario usuario = obtenerUsuario(id);
        usuario.actualizarUbicacion(ubicacion);
        usuarioRepository.save(usuarioAdapter.mapUsuarioToInfrastructure(usuario));
    }

    public void eliminarInteres(Long id, Interes interes) throws Exception {
        Usuario usuario = obtenerUsuario(id);
        usuario.eliminarInteres(interes);
        usuarioRepository.save(usuarioAdapter.mapUsuarioToInfrastructure(usuario));
    }
}
