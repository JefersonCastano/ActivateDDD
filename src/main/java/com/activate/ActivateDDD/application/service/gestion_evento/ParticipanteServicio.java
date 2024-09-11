package com.activate.ActivateDDD.application.service.gestion_evento;

import com.activate.ActivateDDD.domain.gestion_evento.modelo.EventoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ParticipanteServicio {
    public boolean estaDisponible(Long idEvento, Long idParticipante) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<EventoInfo> obtenerEventosParticipante(Long idParticipante) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
