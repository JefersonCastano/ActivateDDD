package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


public class Organizador {
    private Usuario usuario;
    @Getter
    @Setter
    private ArrayList<Evento> eventosOrganizados;

    public Organizador(Usuario usuario) {
        this.usuario = usuario;
        this.eventosOrganizados = new ArrayList<>();
    }

    public boolean crearEvento(Evento evento) {
        return eventosOrganizados.add(evento);
    }

    public boolean cancelarEvento(Long id) throws Exception {
        for (Evento evento : eventosOrganizados) {
            if (evento.getId().equals(id)) {
                evento.cancelar();
                return true;
            }
        }
        throw new Exception("No fue posible cancelar el evento. Evento no encontrado");
    }

    public String getNombre(){
        return usuario.getNombre();
    }
    public Long getId(){ return usuario.getId();}

}
