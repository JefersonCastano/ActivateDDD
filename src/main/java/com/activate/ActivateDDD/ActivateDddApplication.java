package com.activate.ActivateDDD;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.*;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.service.QueryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ActivateDddApplication {

	public static void main(String[] args) {

		//SpringApplication.run(ActivateDddApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(ActivateDddApplication.class, args);
		QueryService queryService = context.getBean(QueryService.class);
		Evento evento = new Evento();

		evento.setNombre("Evento de prueba");
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
	}

}
