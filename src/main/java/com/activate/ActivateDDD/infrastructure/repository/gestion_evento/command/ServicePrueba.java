package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Estado;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.EventoCommand;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Participante;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.repository.EventoCommandRepository;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.sync.EventoSyncService;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Interes;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Thread.sleep;

@Service
public class ServicePrueba {

    @Autowired
    EventoCommandRepository eventoCommandRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EventoSyncService eventoSyncService;


    @Transactional
    public void prueba(){
        EventoCommand eventoCommand = new EventoCommand();
        eventoCommand.setNombre("Evento de prueba");
        eventoCommand.setAforoMaximo(100);
        eventoCommand.setDuracion(2);
        eventoCommand.setDescripcion("Evento de prueba");
        eventoCommand.setFecha(LocalDateTime.now());
        eventoCommand.setUbicacion(new com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Ubicacion(234f,234f));
        eventoCommand.setEstado(com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Estado.ABIERTO);
        eventoCommand.setTipo(com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.TipoEvento.PRIVADO);
        eventoCommand.setOrganizador(1l);
        eventoCommand.getIntereses().add(Interes.ARTE);
        System.out.println("SE CREA EL EVENTO");
        guardarEvento(eventoCommand);

        System.out.println("SE SINCRONIZA CON MONGO");
        eventoSyncService.sync();

        System.out.println("SE OBTIENE EL EVENTO PRIMERA VEZ");
        Optional<EventoCommand> evento1 = eventoCommandRepository.findById(1l);

        System.out.println("SE OBTIENE EL PARTICIPANTE");
        Optional<Usuario> user2 = usuarioRepository.findById(2L);

        com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Participante participante = new com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Participante();
        participante.setUsuario(user2.get());
        participante.setEvento(evento1.get());
        System.out.println("SE AGREGA EL PARTICIPANTE");
        evento1.get().getParticipantes().add(participante);
        System.out.println("SE GUARDA EL EVENTO CON UN PARTICIPANTE");
        EventoCommand eventoCP = guardarEvento(evento1.get());
        imprimirEvento(eventoCP);

        System.out.println("SE OBTIENE EL EVENTO SEGUNDA VEZ");
        Optional<EventoCommand> evento2 = eventoCommandRepository.findById(1l);
        System.out.println("SE BORRAN LOS PARTICIPANTES");
        evento2.get().getParticipantes().clear();
        //evento2.get().getParticipantes().removeIf(p -> p.getId().equals(2l));
        System.out.println(evento2.get().getParticipantes());
        System.out.println("SE GUARDA EL EVENTO SIN PARTICIPANTES");
        eventoCommandRepository.save(evento2.get());

        System.out.println("SE OBTIENE EL EVENTO TERCERA VEZ");
        Optional<EventoCommand> byId = eventoCommandRepository.findById(1l);
        if(!byId.isEmpty()) {
            imprimirEvento(byId.get());
        }

        System.out.println("SE SINCRONIZA CON MONGO UNA ULTIMA VEZ");
        eventoSyncService.sync();
    }
    @Transactional
    public void pruebaAddAndDeleteParticipante(){

        EventoCommand eventoCommand = new EventoCommand();
        eventoCommand.setNombre("Evento de prueba de participantes");
        eventoCommand.setAforoMaximo(1000);
        eventoCommand.setDuracion(2);
        eventoCommand.setDescripcion("Evento para probar la sincronizacion de participantes");
        eventoCommand.setFecha(LocalDateTime.now());
        eventoCommand.setUbicacion(new com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Ubicacion(234f,234f));
        eventoCommand.setEstado(Estado.EN_PROCESO);
        eventoCommand.setTipo(com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.TipoEvento.PRIVADO);
        eventoCommand.setOrganizador(1l);
        eventoCommand.setIntereses(new HashSet<>(Arrays.asList(Interes.ARTE, Interes.CIENCIA, Interes.MUSICA)));
        eventoCommand.setParticipantes(new ArrayList<>(
                Arrays.asList(
                        new Participante(null, usuarioRepository.findById(1l).get(), eventoCommand),
                        new Participante(null, usuarioRepository.findById(2l).get(), eventoCommand),
                        new Participante(null, usuarioRepository.findById(3l).get(), eventoCommand))
        ));

        System.out.println("SE CREA EL EVENTO");
        eventoCommand = guardarEvento(eventoCommand);

        System.out.println("SE AÑADEN PARTICIPANTES");
        eventoCommand.getParticipantes().add(new Participante(null, usuarioRepository.findById(4l).get(), eventoCommand));
        eventoCommand = guardarEvento(eventoCommand);
        eventoSyncService.sync();

        System.out.println("SE ELIMINA UN PARTICIPANTE");
        eventoCommand.getParticipantes().removeIf(participante -> participante.getId().equals(2l));
        eventoCommand = guardarEvento(eventoCommand);

        imprimirEvento(eventoCommand);
        System.out.println("SE SINCRONIZA CON MONGO");
        eventoSyncService.sync();
    }


    public void pruebaAddAndDeleteEvaluaciones(){

        EventoCommand eventoCommand = new EventoCommand();
        eventoCommand.setNombre("Evento de prueba evaluaciones");
        eventoCommand.setAforoMaximo(1000);
        eventoCommand.setDuracion(2);
        eventoCommand.setDescripcion("Evento para probar la sincronizacion de las evaluaciones");
        eventoCommand.setFecha(LocalDateTime.now());
        eventoCommand.setUbicacion(new com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Ubicacion(234f,234f));
        eventoCommand.setEstado(Estado.FINALIZADO);
        eventoCommand.setTipo(com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.TipoEvento.PRIVADO);
        eventoCommand.setOrganizador(1l);
        eventoCommand.setIntereses(new HashSet<>(Arrays.asList(Interes.ARTE, Interes.CIENCIA, Interes.MUSICA)));
        eventoCommand.setParticipantes(new ArrayList<>(
                Arrays.asList(
                        new Participante(null, usuarioRepository.findById(1l).get(), eventoCommand),
                        new Participante(null, usuarioRepository.findById(2l).get(), eventoCommand),
                        new Participante(null, usuarioRepository.findById(3l).get(), eventoCommand))
        ));
        eventoCommand.setEvaluaciones(new ArrayList<>(
                Arrays.asList(
                        new com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Evaluacion(null, "Muy bueno", 5, 1l, eventoCommand),
                        new com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Evaluacion(null, "Muy malo", 1, 2l, eventoCommand),
                        new com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Evaluacion(null, "Regular", 3, 3l, eventoCommand))
        ));

        System.out.println("SE CREA EL EVENTO");
        eventoCommand = guardarEvento(eventoCommand);

        System.out.println("SE AÑADEN EVALUACIONES");
        eventoCommand.getEvaluaciones().add(new com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Evaluacion(null, "Bueno", 4, 1l, eventoCommand));
        eventoCommand = guardarEvento(eventoCommand);
        eventoSyncService.sync();

        System.out.println("SE ELIMINA UNA EVALUACION");
        eventoCommand.getEvaluaciones().remove(0);
        eventoCommand = guardarEvento(eventoCommand);

        imprimirEvento(eventoCommand);
        System.out.println("SE SINCRONIZA CON MONGO");
        eventoSyncService.sync();
    }

    public void imprimirEvento(EventoCommand evento){
        System.out.println("Evento con id: "+evento.getId());
        System.out.println("Aforo maximo: "+evento.getAforoMaximo());
        System.out.println("Duracion: "+evento.getDuracion());
        System.out.println("Nombre: "+evento.getNombre());
        System.out.println("Descripcion: "+evento.getDescripcion());
        System.out.println("Fecha: "+evento.getFecha());
        System.out.println("Ubicacion: "+evento.getUbicacion());
        System.out.println("Estado: "+evento.getEstado());
        System.out.println("Tipo: "+evento.getTipo());
        System.out.println("Organizador: "+evento.getOrganizador());
        System.out.println("Intereses: "+evento.getIntereses());
        System.out.println("Participantes: "+evento.getParticipantes());
        System.out.println("Evaluaciones: "+evento.getEvaluaciones());
    }

    @Transactional
    public EventoCommand guardarEvento(EventoCommand evento){
        return eventoCommandRepository.save(evento);
    }

}
