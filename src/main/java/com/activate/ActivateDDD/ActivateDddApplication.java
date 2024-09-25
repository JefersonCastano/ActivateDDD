package com.activate.ActivateDDD;

import com.activate.ActivateDDD.application.service.gestion_evento.ParticipanteServicio;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evento;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.sync.EventoSyncService;

import com.activate.ActivateDDD.ui.controller.gestion_evento.GestionEventoController;
import com.activate.ActivateDDD.ui.controller.gestion_evento.OrganizadorController;
import com.activate.ActivateDDD.ui.controller.gestion_usuario.GestionUsuarioController;
import com.activate.ActivateDDD.ui.controller.recomendacion.RecomendacionController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication(scanBasePackages = "com.activate.ActivateDDD")
public class ActivateDddApplication {

	public static void main(String[] args) throws Exception {

		//SpringApplication.run(ActivateDddApplication.class, args);
		System.out.println("Iniciando aplicación");
		ConfigurableApplicationContext context = SpringApplication.run(ActivateDddApplication.class, args);
		System.out.println("Aplicación iniciada");

		//------------ Servicio Recomendacion ------------
		RecomendacionController recomendacionController = context.getBean(RecomendacionController.class);

		//----------- Gestion Usuario ------------
		GestionUsuarioController gestionUsuarioController = context.getBean(GestionUsuarioController.class);

		//Creacion Usuarios
		for (int i = 1; i <= 10; i++) {
			gestionUsuarioController.crearUsuario("Usuario " + i, 20 + i, "usuario" + i + "@example.com", new HashSet<>(Arrays.asList(
					com.activate.ActivateDDD.domain.commons.Interes.TECNOLOGIA,
					com.activate.ActivateDDD.domain.commons.Interes.MUSICA,
					com.activate.ActivateDDD.domain.commons.Interes.CIENCIA)),
					Double.valueOf(2.0 + i * 0.1).longValue(), Double.valueOf(4.0 + i * 0.1).longValue());
		}
		try{
			recomendacionController.emparejar(1L);
		}catch (Exception e){
			System.out.println("Error al emparejar: " + e.getMessage());
		}

		//Obtener Usuario
		Usuario usuario = gestionUsuarioController.obtenerUsuario(1L);
		System.out.println("IMPRIMIENDO USUARIO");
		System.out.println(usuario.getNombre() + " " + usuario.getEdad() + " " + usuario.getEmail() + " " + usuario.getIntereses() + " " + usuario.getUbicacion());

		//Obtener Usuarios
		ArrayList<Usuario> usuarios = gestionUsuarioController.obtenerUsuarios();
		System.out.println("IMPRIMIENDO USUARIOS");
		usuarios.forEach(u -> System.out.println(u.getNombre() + " " + u.getEdad() + " " + u.getEmail() + " " + u.getIntereses() + " " + u.getUbicacion()));

		//Editar Perfil
		gestionUsuarioController.editarPerfil(8L, "Nombre editado", 100, "editado@gmail.com");

		//Agregar Interes
		gestionUsuarioController.agregarInteres(3L, Interes.DEPORTE);

		//Actualizar Ubicacion
		gestionUsuarioController.actualizarUbicacion(5L, new Ubicacion(3, 4));

		//Eliminar Interes
		gestionUsuarioController.agregarInteres(1L, Interes.GASTRONOMIA);
		gestionUsuarioController.eliminarInteres(1L, Interes.TECNOLOGIA);


		//----------- Gestion Evento / Servicio Organizador ------------


		OrganizadorController organizadorController = context.getBean(OrganizadorController.class);
		EventoSyncService eventoSyncService = context.getBean(EventoSyncService.class);

		//Creacion Evento

		organizadorController.crearEvento(50, 200, "Primer Evento", "Ven a divertirte",
				LocalDateTime.now().plusMinutes(6), 2d, 5d, TipoEvento.PUBLICO,
				2L, new HashSet<>(Arrays.asList(Interes.GASTRONOMIA)));

		organizadorController.crearEvento(100, 120, "Evento de prueba", "Evento de prueba",
				LocalDateTime.now().plusMinutes(2), 234d, 234d, TipoEvento.PRIVADO,
				1L, new HashSet<>(Arrays.asList(Interes.ARTE)));

		organizadorController.crearEvento(1000, 180, "Evento de prueba de participantes", "Evento de prueba de participantes",
				LocalDateTime.now().plusMinutes(4), 8d, 4d, TipoEvento.PRIVADO,
				1L, new HashSet<>(Arrays.asList(Interes.ARTE, Interes.VIDEOJUEGOS, Interes.MUSICA)));

		organizadorController.crearEvento(1000, 200, "Evento de prueba de evaluaciones", "Evento de prueba de evaluaciones",
				LocalDateTime.now().plusDays(2), 4d, 15d, TipoEvento.PRIVADO,
				1L, new HashSet<>(Arrays.asList(Interes.ARTE, Interes.CIENCIA, Interes.MUSICA)));

		eventoSyncService.sync();

		recomendacionController.recomendarEvento(1L);
		recomendacionController.recomendarEvento(2L);
		recomendacionController.recomendarEvento(3L);
		recomendacionController.recomendarEvento(4L);

		//Cancelar Evento
		organizadorController.cancelarEvento(1L, 2L);

		//------ Gestion Eventos --------

		GestionEventoController gestionEventoController = context.getBean(GestionEventoController.class);

		//Obtener Evento
		com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evento evento = gestionEventoController.obtenerEvento(1L);
		System.out.println("IMPRIMIENDO EVENTO");
		System.out.println(evento.getNombre() + " " + evento.getDescripcion() + " " + evento.getFecha() + " " + evento.getUbicacion() + " " + evento.getIntereses());

		//Obtener Eventos
		ArrayList<Evento> eventos = gestionEventoController.obtenerEventos();
		System.out.println("IMPRIMIENDO EVENTOS");
		eventos.forEach(e -> System.out.println(e.getNombre() + " " + e.getDescripcion() + " " + e.getFecha() + " " + e.getUbicacion() + " " + e.getIntereses()));

		//Actualizar Tipo
		gestionEventoController.actualizarTipo(1L);
		eventoSyncService.sync();

		//Actualizar Aforo Maximo
		gestionEventoController.actualizarAforoMaximo(1L, 2);
		eventoSyncService.sync();

		//Actualizar Fecha
		gestionEventoController.actualizarFecha(1L, LocalDateTime.now().plusDays(2));
		eventoSyncService.sync();

		//Agregar Participante
		gestionEventoController.agregarParticipante(2L, 8L);

		gestionEventoController.agregarParticipante(3L, 1L);
		eventoSyncService.sync();
    	gestionEventoController.agregarParticipante(3L, 2L);
		gestionEventoController.agregarParticipante(3L, 3L);
		gestionEventoController.agregarParticipante(3L, 4L);

		gestionEventoController.agregarParticipante(4L, 1L);
		eventoSyncService.sync();
		gestionEventoController.agregarParticipante(4L, 2L);
		gestionEventoController.agregarParticipante(4L, 3L);
		eventoSyncService.sync();

		//Inserciones erroneas
		try {
			gestionEventoController.agregarParticipante(1L, 1L);
		} catch (Exception e) {
			System.out.println("Error al agregar participante: " + e.getMessage());
		}
		try {
			gestionEventoController.agregarParticipante(2L, 1L);
		} catch (Exception e) {
			System.out.println("Error al agregar participante: " + e.getMessage());
		}

		//Eliminar Participante
		gestionEventoController.eliminarParticipante(2L, 8L);
		gestionEventoController.eliminarParticipante(3L, 2L);
		eventoSyncService.sync();

		//Iniciar Evento
		gestionEventoController.iniciarEvento(3L);
		gestionEventoController.iniciarEvento(4L);
		eventoSyncService.sync();

		//Finalizar Evento
		gestionEventoController.finalizarEvento(4L);
		eventoSyncService.sync();

		//Agregar Evaluacion
		gestionEventoController.agregarEvaluacion(4L, "Muy Bueno", 5, 1L);
		gestionEventoController.agregarEvaluacion(4L, "Regular", 3, 2L);
		gestionEventoController.agregarEvaluacion(4L, "Muy Malo", 1, 3L);
		eventoSyncService.sync();

		//Obtener Evento 4
		com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evento eventoConsulta = gestionEventoController.obtenerEvento(4L);
		System.out.println("IMPRIMIENDO EVENTO");
		System.out.println(eventoConsulta.getNombre() + " " + eventoConsulta.getDescripcion() + " " + eventoConsulta.getFecha() + " " + eventoConsulta.getUbicacion() + " " + eventoConsulta.getIntereses());
		eventoConsulta.getParticipantes().forEach(p -> System.out.println(p.getIdUsuario()));
		eventoConsulta.getEvaluaciones().forEach(e -> System.out.println(e.getComentario() + " " + e.getPuntuacion() + " " + e.getAutor()));

		//------- Servicio Participante -----------
		ParticipanteServicio participanteServicio = context.getBean(ParticipanteServicio.class);

		//Obtener Eventos Participante
		ArrayList<EventoInfo> eventosParticipante = participanteServicio.obtenerEventosParticipante(1L);
		System.out.println("IMPRIMIENDO EVENTOS PARTICIPANTE 1");
		eventosParticipante.forEach(e -> System.out.println(e.getNombre()));

		//------------ Servicio Recomendacion ------------
		try{
			System.out.println("Recomendaciones Usuario 1.  Intereses: " + gestionUsuarioController.obtenerUsuario(1L).getIntereses());
			recomendacionController.getRecomendaciones(1L).forEach(e -> System.out.println("Nombre: "+ e.getNombre() +
					"Ubicacion: " + e.getUbicacion() + "Intereses: " + e.getIntereses()));
		}catch (Exception e){
			System.out.println("Error al obtener recomendaciones: " + e.getMessage());
		}

		try{
			System.out.println("Recomendaciones Usuario 2.  Intereses: " + gestionUsuarioController.obtenerUsuario(2L).getIntereses());
			recomendacionController.getRecomendaciones(2L).forEach(e -> System.out.println("Nombre: "+ e.getNombre() +
					"Ubicacion: " + e.getUbicacion() + "Intereses: " + e.getIntereses()));
		}catch (Exception e){
			System.out.println("Error al obtener recomendaciones: " + e.getMessage());
		}
	}
}
