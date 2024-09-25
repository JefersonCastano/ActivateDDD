package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Getter
public class Participante {
    private Long id;
    @Getter
    private Usuario usuario;
    @Setter
    @Getter
    private ArrayList<EventoInfo> eventosParticipados;

    public Participante(Long id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
        this.eventosParticipados = new ArrayList<>();
    }

    public boolean estaDisponible(EventoInfo evento) {
        for (EventoInfo e : eventosParticipados) {
            if (e.getFecha().truncatedTo(ChronoUnit.MINUTES).equals(evento.getFecha().truncatedTo(ChronoUnit.MINUTES)))  return false;
            if (e.getFecha().isAfter(evento.getFecha()) && e.getFecha().isBefore(evento.getFecha().plusMinutes(evento.getDuracion()))) return false;
            if (evento.getFecha().isAfter(e.getFecha()) && evento.getFecha().isBefore(e.getFecha().plusMinutes(e.getDuracion()))) return false;
        }
        return true;
    }
}