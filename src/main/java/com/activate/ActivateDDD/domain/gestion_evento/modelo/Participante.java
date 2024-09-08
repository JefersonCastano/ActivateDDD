package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;

import java.util.ArrayList;

public class Participante {
    private Long id;
    private Usuario usuario;
    private ArrayList<EventoInfo> eventosParticipados;

    public Participante(Long id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
        this.eventosParticipados = new ArrayList<>();
    }

    public boolean estaDisponible(EventoInfo evento) {
        for (EventoInfo e : eventosParticipados) {
            if (e.getFecha().equals(evento.getFecha())) {
                return false;
            }
            if(e.getFecha().isAfter(evento.getFecha()) && e.getFecha().isBefore(evento.getFecha().plusMinutes(evento.getDuracion()))) {
                return false;
            }
        }
        return true;
    }

}