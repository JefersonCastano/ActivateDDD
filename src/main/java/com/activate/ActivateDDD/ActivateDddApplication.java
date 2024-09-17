package com.activate.ActivateDDD;


import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.*;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.service.QueryService;

import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Interes;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Ubicacion;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import java.util.HashSet;


@SpringBootApplication
public class ActivateDddApplication {

	public static void main(String[] args) {


		//SpringApplication.run(ActivateDddApplication.class, args);
		System.out.println("Iniciando aplicación");
		ConfigurableApplicationContext context = SpringApplication.run(ActivateDddApplication.class, args);
		System.out.println("Aplicación iniciada");

		QueryService queryService = context.getBean(QueryService.class);

		System.out.println("Creando evento de prueba");
		Evento evento = new Evento();

		evento.setNombre("Evento de prueba2");
		evento.setAforoMaximo(100);
		evento.setDuracion(2);
		evento.setDescripcion("Evento de prueba");
		evento.setFecha(LocalDateTime.now());
		evento.setUbicacion(new Ubicacion(234f,234f));
		evento.setEstado(Estado.ABIERTO);
		evento.setTipo(TipoEvento.PRIVADO);
		evento.setOrganizador(new Organizador(234l,"Organizador de prueba"));
		evento.setIntereses(Set.of(Interes.ARTE,Interes.CIENCIA));
		evento.setParticipantes(List.of(new Participante(234l,"Participante de prueba"), new Participante(235l,"Participante de prueba 2")));
		evento.setEvaluaciones(List.of(new Evaluacion(234l,"muy bueno", 5, "pepe"), new Evaluacion(235l,"muy malo", 1, "juan")));


		Evento eventoCreado = queryService.createEvento(evento);
		System.out.println("Evento creado: " + queryService.getEventoById(eventoCreado.getId()));

		queryService.getEventos().forEach(System.out::println);


		//Creacion Usuario

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
