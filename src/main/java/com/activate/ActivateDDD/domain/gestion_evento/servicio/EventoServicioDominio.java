package com.activate.ActivateDDD.domain.gestion_evento.servicio;

import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Evento;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante;

import java.time.LocalDateTime;

public class EventoServicioDominio {

    public boolean agregarParticipante(Evento evento, Participante participante){
        EventoInfo e=convertirEventoAInfo(evento);
        if(!participante.estaDisponible(e))
            throw new RuntimeException("El participante ya está inscrito en un evento en la misma fecha y hora: "+e.getFecha().toString());

        return evento.agregarParticipante(participante);
    }

    public boolean eliminarParticipante(Evento evento,Participante participante){
        return evento.eliminarParticipante(participante);
    }

    public EventoInfo convertirEventoAInfo(Evento evento){
        return new EventoInfo(evento.getId(), evento.getAforoMaximo(), evento.getDuracion(), evento.getNombre(), evento.getDescripcion(), evento.getFecha(), evento.getUbicacion(), evento.getEstado(), evento.getTipo(), evento.getNombreOrganizador(), evento.getIntereses());
    }


}
