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

    public boolean estaDisponible(Long idEvento, Long idParticipante) {
        return participanteServicio.estaDisponible(idEvento, idParticipante);
    }

    public ArrayList<EventoInfo> obtenerEventosParticipante(Long idParticipante) {
        return participanteServicio.obtenerEventosParticipante(idParticipante);
    }
}
