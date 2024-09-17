package com.activate.ActivateDDD;

import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Interes;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Ubicacion;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashSet;

@SpringBootApplication
public class ActivateDddApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(ActivateDddApplication.class, args);
		UsuarioRepository usuarioRepository = context.getBean(UsuarioRepository.class);

		// Crear un nuevo usuario
		Usuario usuario = new Usuario();
		usuario.setNombre("Juan Perez");
		usuario.setEdad(30);
		usuario.setEmail("juan.perez@example.com");

		// Establecer intereses
		HashSet<Interes> intereses = new HashSet<>();
		intereses.add(Interes.TECNOLOGIA);
		intereses.add(Interes.MUSICA);
		usuario.setIntereses(intereses);

		// Establecer ubicación
		Ubicacion ubicacion = new Ubicacion(2.2, 4.4);
		usuario.setUbicacion(ubicacion);

		// Guardar el usuario en la base de datos
		usuarioRepository.save(usuario);
	}
}
