package com.activate.ActivateDDD.application.service.gestion_evento;

import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GestionEventoServicio {
    public void actualizarEstado(Long idEvento, Estado estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actualizarTipo(Long idEvento, TipoEvento tipo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actualizarAforoMaximo(Long idEvento, int aforoMaximo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actualizarFecha(Long idEvento, LocalDateTime fecha) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void agregarEvaluacion(Long idEvento, String comentario, int puntuacion, Long idParticipante) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void agregarParticipante(Long idEvento, Long idParticipante) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void eliminarParticipante(Long idEvento, Long idParticipante) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
