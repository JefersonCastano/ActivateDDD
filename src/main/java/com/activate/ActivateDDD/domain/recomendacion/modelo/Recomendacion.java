package com.activate.ActivateDDD.domain.recomendacion.modelo;

import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;

public class Recomendacion {

    private static final int UMBRAL_CERCANIA = 50;
    private HashMap<Long,ArrayList<EventoInfo>> emparejamientos;

    public Recomendacion() {
        this.emparejamientos = new HashMap<>();
    }

    public void emparejar(Usuario usuario, ArrayList<EventoInfo> eventosDisponibles) {
        ArrayList<EventoInfo> eventos = new ArrayList<>();
        for (EventoInfo evento : eventosDisponibles) {
            for (Interes interes : usuario.getIntereses()) {
                if (evento.getIntereses().contains(interes) && estaCerca(usuario.getUbicacion(), evento.getUbicacion())) {
                    eventos.add(evento);
                }
            }
        }
        if(eventos.isEmpty())
            throw new RuntimeException("No hay eventos recomendables para el usuario");
        if(emparejamientos.containsKey(usuario.getId()))
            emparejamientos.replace(usuario.getId(), eventos);
        else
            emparejamientos.put(usuario.getId(), eventos);
    }

    public void recomendarEvento(EventoInfo evento, ArrayList<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            for (Interes interes : usuario.getIntereses()) {
                if (evento.getIntereses().contains(interes) && estaCerca(usuario.getUbicacion(), evento.getUbicacion())) {
                    if(emparejamientos.containsKey(usuario.getId())) {
                        ArrayList<EventoInfo> eventos = emparejamientos.get(usuario.getId());
                        eventos.add(evento);
                        emparejamientos.replace(usuario.getId(), eventos);
                    } else {
                        ArrayList<EventoInfo> eventos = new ArrayList<>();
                        eventos.add(evento);
                        emparejamientos.put(usuario.getId(), eventos);
                    }
                }
            }
        }

    }

    public void eliminarEmparejamiento(Usuario usuario,EventoInfo evento){
        if(emparejamientos.containsKey(usuario.getId())){
            ArrayList<EventoInfo> eventos = emparejamientos.get(usuario.getId());
            eventos.remove(evento);
            emparejamientos.replace(usuario.getId(), eventos);
        }
    }

    private boolean estaCerca(Ubicacion A, Ubicacion B) {
        int distancia = (int) Math.sqrt(Math.pow(A.getLatitud() - B.getLatitud(), 2) + Math.pow(A.getLongitud() - B.getLongitud(), 2));
        return distancia <= UMBRAL_CERCANIA;
    }

}