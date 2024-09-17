package com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository;

import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
