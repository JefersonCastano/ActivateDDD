package com.activate.ActivateDDD.application.service.recomendacion;

import com.activate.ActivateDDD.application.service.gestion_evento.GestionEventoServicio;
import com.activate.ActivateDDD.application.service.gestion_usuario.GestionUsuarioServicio;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import com.activate.ActivateDDD.domain.gestion_evento.servicio.EventoServicioDominio;
import com.activate.ActivateDDD.domain.recomendacion.modelo.Recomendacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RecomendacionServicio {

    Recomendacion recomendacion = new Recomendacion();
    @Autowired
    GestionEventoServicio gestionEventoServicio;
    @Autowired
    GestionUsuarioServicio gestionUsuarioServicio;
    @Autowired
    EventoServicioDominio eventoServicioDominio;

    public ArrayList<EventoInfo> emparejar(Long idUsuario) throws Exception {
        ArrayList<EventoInfo> eventos = new ArrayList<>();
        gestionEventoServicio.obtenerEventosDomain().forEach(evento -> eventos.add(eventoServicioDominio.convertirEventoAInfo(evento)));
        recomendacion.emparejar(gestionUsuarioServicio.obtenerUsuario(idUsuario), eventos);
        return recomendacion.getRecomendaciones(idUsuario);
    }

    public void recomendarEvento(Long idEvento) throws Exception {
        EventoInfo evento = eventoServicioDominio.convertirEventoAInfo(gestionEventoServicio.obtenerEventoDomain(idEvento));
        recomendacion.recomendarEvento(evento, gestionUsuarioServicio.obtenerUsuarios());
    }

    public ArrayList<EventoInfo> getRecomendaciones(Long idUsuario) {
        return recomendacion.getRecomendaciones(idUsuario);
    }

}
