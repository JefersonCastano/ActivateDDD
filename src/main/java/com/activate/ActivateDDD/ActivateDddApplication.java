package com.activate.ActivateDDD;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.ServicePrueba;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.repository.EventoRepository;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.sync.EventoSyncService;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Interes;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Ubicacion;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository.UsuarioRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;

@SpringBootApplication(scanBasePackages = "com.activate.ActivateDDD")
public class ActivateDddApplication {


	public static void main(String[] args) {


		//SpringApplication.run(ActivateDddApplication.class, args);
		System.out.println("Iniciando aplicación");
		ConfigurableApplicationContext context = SpringApplication.run(ActivateDddApplication.class, args);
		System.out.println("Aplicación iniciada");

		//Creacion Usuario

		UsuarioRepository usuarioRepository = context.getBean(UsuarioRepository.class);

		for (int i = 1; i <= 10; i++) {
			// Crear un nuevo usuario
			Usuario usuario = new Usuario();
			usuario.setNombre("Usuario " + i);
			usuario.setEdad(20 + i); // Edad entre 21 y 30
			usuario.setEmail("usuario" + i + "@example.com");

			// Establecer intereses
			HashSet<Interes> intereses = new HashSet<>();
			intereses.add(Interes.TECNOLOGIA);
			intereses.add(Interes.MUSICA);
			usuario.setIntereses(intereses);

			// Establecer ubicación (puedes variar las coordenadas si lo deseas)
			Ubicacion ubicacion = new Ubicacion(2.0 + i * 0.1, 4.0 + i * 0.1);
			usuario.setUbicacion(ubicacion);

			// Guardar el usuario en la base de datos
			usuarioRepository.save(usuario);
		}

		ServicePrueba servicePrueba = context.getBean(ServicePrueba.class);

		servicePrueba.prueba();
		servicePrueba.pruebaAddAndDeleteParticipante();
		servicePrueba.pruebaAddAndDeleteEvaluaciones();

		EventoSyncService eventoSyncService = context.getBean(EventoSyncService.class);
		eventoSyncService.sync();

		EventoRepository eventoRepository = context.getBean(EventoRepository.class);
		eventoRepository.findAll().forEach(System.out::println);
	}
}
