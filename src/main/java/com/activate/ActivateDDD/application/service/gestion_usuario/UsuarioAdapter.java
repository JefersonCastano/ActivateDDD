package com.activate.ActivateDDD.application.service.gestion_usuario;

import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioAdapter {
    @Autowired
    UsuarioRepository usuarioRepository;

    public com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario mapUsuarioToInfrastructure(Usuario usuario) {
        com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario usuarioMapped = usuarioRepository.findById(usuario.getId())
                .orElse(new com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario());

        //usuarioMapped.setId(usuario.getId());
        usuarioMapped.setNombre(usuario.getNombre());
        usuarioMapped.setEdad(usuario.getEdad());
        usuarioMapped.setEmail(usuario.getEmail());
        Set<com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Interes> interesesMapped = new HashSet<>();
        for (Interes interes : usuario.getIntereses()) {
            interesesMapped.add(com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Interes.valueOf(interes.toString()));
        }
        usuarioMapped.setIntereses(interesesMapped);
        usuarioMapped.setUbicacion(new com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Ubicacion(usuario.getUbicacion().getLatitud(), usuario.getUbicacion().getLongitud()));
        return usuarioMapped;
    }

    public Usuario mapUsuarioToDomain (com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario usuario) throws Exception {
        HashSet<Interes> interesesMapped = new HashSet<>();
        for (com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Interes interesMapped : usuario.getIntereses()) {
            interesesMapped.add(Interes.valueOf(interesMapped.toString()));
        }
        return new Usuario(usuario.getId(), usuario.getNombre(), usuario.getEdad(), usuario.getEmail(), interesesMapped, new Ubicacion(usuario.getUbicacion().getLatitud(), usuario.getUbicacion().getLongitud()));
    }
}
