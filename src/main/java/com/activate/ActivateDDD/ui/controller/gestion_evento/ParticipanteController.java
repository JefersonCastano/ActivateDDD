package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.ParticipanteServicio;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ParticipanteController {

    @Autowired
    private ParticipanteServicio participanteServicio;

    public ArrayList<EventoInfo> obtenerEventosParticipante(Long idParticipante) throws Exception {
        return participanteServicio.obtenerEventosParticipante(idParticipante);
    }
}
