package com.activate.ActivateDDD;

import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Evento;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.ServicePrueba;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Estado;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.EventoCommand;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.repository.EventoQueryRepository;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.sync.EventoSyncService;

import com.activate.ActivateDDD.ui.controller.gestion_evento.GestionEventoController;
import com.activate.ActivateDDD.ui.controller.gestion_evento.OrganizadorController;
import com.activate.ActivateDDD.ui.controller.gestion_usuario.GestionUsuarioController;
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


		//----------- Gestion Evento / Organizador ------------



//		OrganizadorController organizadorController = context.getBean(OrganizadorController.class);
//		EventoSyncService eventoSyncService = context.getBean(EventoSyncService.class);
//
//		//Creacion Evento
//
//		organizadorController.crearEvento(50, 3, "Primer Evento", "Ven a divertirte",
//				LocalDateTime.now().plusDays(1), 2d, 23d, TipoEvento.PUBLICO,
//				2L, new HashSet<>(Arrays.asList(Interes.VIDEOJUEGOS)));
//
//		organizadorController.crearEvento(100, 2, "Evento de prueba", "Evento de prueba",
//				LocalDateTime.now().plusSeconds(3), 234d, 234d, TipoEvento.PRIVADO,
//				1L, new HashSet<>(Arrays.asList(Interes.ARTE)));
//
//		organizadorController.crearEvento(1000, 2, "Evento de prueba de participantes", "Evento de prueba de participantes",  //Deberia estar en proceso
//				LocalDateTime.now().plusSeconds(3), 234d, 234d, TipoEvento.PRIVADO,
//				1L, new HashSet<>(Arrays.asList(Interes.ARTE, Interes.CIENCIA, Interes.MUSICA)));
//
//		organizadorController.crearEvento(1000, 2, "Evento de prueba de evaluaciones", "Evento de prueba de evaluaciones",  //Toca ponerlo en Finalizado
//				LocalDateTime.now().plusSeconds(3), 234d, 234d, TipoEvento.PRIVADO,
//				1L, new HashSet<>(Arrays.asList(Interes.ARTE, Interes.CIENCIA, Interes.MUSICA)));
//
//		eventoSyncService.sync();
//
//		//Cancelar Evento
//		organizadorController.cancelarEvento(1L, 2L);
//
//		//------ Gestion Eventos --------
//
//		GestionEventoController gestionEventoController = context.getBean(GestionEventoController.class);
//
//		//Obtener Evento
//		Evento evento = gestionEventoController.obtenerEvento(1L);
//		System.out.println("IMPRIMIENDO EVENTO");
//		System.out.println(evento.getNombre() + " " + evento.getDescripcion() + " " + evento.getFecha() + " " + evento.getUbicacion() + " " + evento.getIntereses());
//
//		//Obtener Eventos
//		ArrayList<Evento> eventos = gestionEventoController.obtenerEventos();
//		System.out.println("IMPRIMIENDO EVENTOS");
//		eventos.forEach(e -> System.out.println(e.getNombre() + " " + e.getDescripcion() + " " + e.getFecha() + " " + e.getUbicacion() + " " + e.getIntereses()));
//
//		//Actualizar Tipo
//		gestionEventoController.actualizarTipo(1L);
//		eventoSyncService.sync();
//
//		//Actualizar Aforo Maximo
//		gestionEventoController.actualizarAforoMaximo(1L, 2);
//		eventoSyncService.sync();
//
//		//Actualizar Fecha
//		gestionEventoController.actualizarFecha(1L, LocalDateTime.now().plusDays(2));
//		eventoSyncService.sync();
//
//		//Agregar Participante
//		gestionEventoController.agregarParticipante(1L, 2L);
//		eventoSyncService.sync();
//		gestionEventoController.agregarParticipante(1L, 3L);
//		eventoSyncService.sync();
//
//		//Eliminar Participante
//		gestionEventoController.eliminarParticipante(1L, 3L);  //TODO: no funciona

		//Agregar Evaluacion
		//gestionEventoController.agregarEvaluacion(1L, "Muy bueno", 5, 2L);


		EventoSyncService eventoSyncService = context.getBean(EventoSyncService.class);

		ServicePrueba servicePrueba = context.getBean(ServicePrueba.class);
		servicePrueba.prueba();

		servicePrueba.pruebaAddAndDeleteParticipante();
		servicePrueba.pruebaAddAndDeleteEvaluaciones();

		eventoSyncService.sync();

		EventoQueryRepository eventoQueryRepository = context.getBean(EventoQueryRepository.class);
		eventoQueryRepository.findAll().forEach(System.out::println);
	}
}
