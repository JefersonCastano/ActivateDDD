package com.activate.ActivateDDD.domain.recomendacion.modelo;

import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import com.activate.ActivateDDD.domain.gestion_usuario.modelo.Usuario;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recomendacion {

    private static final int UMBRAL_CERCANIA = 50;
    private HashMap<Long,ArrayList<EventoInfo>> emparejamientos;

    public Recomendacion() {
        this.emparejamientos = new HashMap<>();
    }

    public void emparejar(Usuario usuario, ArrayList<EventoInfo> eventosDisponibles) {
        ArrayList<EventoInfo> eventos = new ArrayList<>();
        for (EventoInfo evento : eventosDisponibles) {
            boolean estaCerca = estaCerca(usuario.getUbicacion(), evento.getUbicacion());
            for (Interes interes : usuario.getIntereses()) {
                if (evento.getIntereses().contains(interes) && estaCerca) {
                    eventos.add(evento);
                    break;
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
            boolean estaCerca = estaCerca(usuario.getUbicacion(), evento.getUbicacion());
            for (Interes interes : usuario.getIntereses()) {
                if(emparejamientos.containsKey(usuario.getId()) && emparejamientos.get(usuario.getId()).stream().anyMatch(e -> e.getId().equals(evento.getId()))) break;
                if (evento.getIntereses().contains(interes) && estaCerca) {
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

    private boolean estaCerca(Ubicacion A, Ubicacion B) {
        int distancia = (int) Math.sqrt(Math.pow(A.getLatitud() - B.getLatitud(), 2) + Math.pow(A.getLongitud() - B.getLongitud(), 2));
        return distancia <= UMBRAL_CERCANIA;
    }

    public ArrayList<EventoInfo> getRecomendaciones(Long idUsuario) {
        if(!emparejamientos.containsKey(idUsuario))
            throw new RuntimeException("No hay recomendaciones actualmente para el usuario");
        return emparejamientos.get(idUsuario);
    }

}