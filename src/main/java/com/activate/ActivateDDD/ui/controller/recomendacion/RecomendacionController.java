package com.activate.ActivateDDD.ui.controller.recomendacion;

import com.activate.ActivateDDD.application.service.recomendacion.RecomendacionServicio;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class RecomendacionController {

    @Autowired
    RecomendacionServicio recomendacionServicio;

    public ArrayList<EventoInfo> emparejar(Long idUsuario) throws Exception {
        return recomendacionServicio.emparejar(idUsuario);
    }
    public void recomendarEvento(Long idEvento) throws Exception {
        recomendacionServicio.recomendarEvento(idEvento);
    }
    public ArrayList<EventoInfo> getRecomendaciones(Long idUsuario) {
        return recomendacionServicio.getRecomendaciones(idUsuario);
    }

}