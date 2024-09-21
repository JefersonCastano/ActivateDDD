package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command;

import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.EventoCommand;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.repository.EventoCommandRepository;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Interes;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class ServicePrueba {

    @Autowired
    EventoCommandRepository eventoCommandRepository;
    @Autowired
    UsuarioRepository usuarioRepository;


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
        eventoCommandRepository.save(eventoCommand);

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
        eventoCommandRepository.save(evento1.get());

        System.out.println("SE OBTIENE EL EVENTO SEGUNDA VEZ");
        Optional<EventoCommand> evento2 = eventoCommandRepository.findById(1l);
        System.out.println("SE BORRAN LOS PARTICIPANTES");
        evento2.get().getParticipantes().clear();
        //eventoCommandRepository.delete(evento2.get());
        System.out.println(evento2.get().getParticipantes());
        System.out.println("SE GUARDA EL EVENTO SIN PARTICIPANTES");
        eventoCommandRepository.save(evento2.get());

        System.out.println("SE OBTIENE EL EVENTO TERCERA VEZ");
        Optional<EventoCommand> byId = eventoCommandRepository.findById(1l);
        if(!byId.isEmpty()){
            System.out.println(byId.get().getId());
            System.out.println(byId.get().getNombre());
            System.out.println(byId.get().getDescripcion());
            System.out.println(byId.get().getFecha());
            System.out.println(byId.get().getUbicacion());
            System.out.println(byId.get().getEstado());
            System.out.println(byId.get().getTipo());
            System.out.println(byId.get().getOrganizador());
            System.out.println(byId.get().getIntereses());
            System.out.println(byId.get().getParticipantes());
            System.out.println(byId.get().getEvaluaciones());

        }

    }


}
