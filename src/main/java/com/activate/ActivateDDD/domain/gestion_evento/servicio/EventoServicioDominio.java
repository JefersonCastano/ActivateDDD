package com.activate.ActivateDDD.domain.gestion_evento.servicio;

import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Evento;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventoServicioDominio {

    public boolean agregarParticipante(Evento evento, Participante participante){
        EventoInfo e = convertirEventoAInfo(evento);
        if(!participante.estaDisponible(e))
            throw new RuntimeException("El participante ya está inscrito en un evento en la misma fecha y hora: "+e.getFecha().toString());
        if (evento.agregarParticipante(participante)){
            participante.getEventosParticipados().add(e);
            return true;
        }
        return false;
    }

    public EventoInfo convertirEventoAInfo(Evento evento){
        return new EventoInfo(evento.getId(), evento.getAforoMaximo(), evento.getDuracion(), evento.getNombre(), evento.getDescripcion(), evento.getFecha(), evento.getUbicacion(), evento.getEstado(), evento.getTipo(), evento.getNombreOrganizador(), evento.getIntereses());
    }


}
