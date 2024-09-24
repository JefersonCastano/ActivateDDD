package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import lombok.Getter;

import java.util.ArrayList;


public class Organizador {
    private Long id;
    private Usuario usuario;
    @Getter
    private ArrayList<Evento> eventosOrganizados;

    public Organizador(Long id,Usuario usuario,Evento evento) {
        this.id=id;
        this.usuario = usuario;
        this.eventosOrganizados = new ArrayList<>();
        crearEvento(evento);
    }

    public boolean crearEvento(Evento evento) {
        return eventosOrganizados.add(evento);
    }

    public boolean cancelarEvento(Long id) {
        for (Evento evento : eventosOrganizados) {
            if (evento.getId().equals(id)) {
                evento.cancelar();
                return true;
            }
        }
        return false;
    }

    public String getNombre(){
        return usuario.getNombre();
    }

}
