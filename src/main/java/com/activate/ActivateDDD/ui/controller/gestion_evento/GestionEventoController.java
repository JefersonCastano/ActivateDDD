package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.GestionEventoServicio;
import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.gestion_evento.modelo.Evaluacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class GestionEventoController {
    /**
     * Objeto de la capa de aplicación
     */
    @Autowired
    private GestionEventoServicio gestionEventoServicio;

    public void actualizarEstado (Long idEvento, Estado estado) {
        gestionEventoServicio.actualizarEstado(idEvento, estado);
    }

    public void actualizarTipo (Long idEvento, TipoEvento tipo) {
        gestionEventoServicio.actualizarTipo(idEvento, tipo);
    }

    public void actualizarAforoMaximo (Long idEvento, int aforoMaximo) {
        gestionEventoServicio.actualizarAforoMaximo(idEvento, aforoMaximo);
    }

    public void actualizarFecha(Long idEvento, LocalDateTime fecha) {
        gestionEventoServicio.actualizarFecha(idEvento, fecha);
    }

    public void agregarEvaluacion(Long idEvento, String comentario, int puntuacion, Long idParticipante) {
        gestionEventoServicio.agregarEvaluacion(idEvento, comentario, puntuacion, idParticipante);
    }

    public void agregarParticipante(Long idEvento, Long idParticipante) {
        gestionEventoServicio.agregarParticipante(idEvento, idParticipante);
    }

    public void eliminarParticipante(Long idEvento, Long idParticipante) {
        gestionEventoServicio.eliminarParticipante(idEvento, idParticipante);
    }

}
