package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.GestionEventoServicio;
import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
public class GestionEventoController {
    /**
     * Objeto de la capa de aplicación
     */
    @Autowired
    private GestionEventoServicio gestionEventoServicio;

    public com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model.Evento obtenerEvento(Long idEvento) throws Exception {
        return gestionEventoServicio.obtenerEvento(idEvento);
    }

    public ArrayList<Evento> obtenerEventos() throws Exception {
        return gestionEventoServicio.obtenerEventos();
    }

    public void actualizarTipo (Long idEvento) throws Exception {
        gestionEventoServicio.actualizarTipo(idEvento);
    }

    public void actualizarAforoMaximo (Long idEvento, int aforoMaximo) throws Exception {
        gestionEventoServicio.actualizarAforoMaximo(idEvento, aforoMaximo);
    }

    public void actualizarFecha(Long idEvento, LocalDateTime fecha) throws Exception {
        gestionEventoServicio.actualizarFecha(idEvento, fecha);
    }

    public void agregarEvaluacion(Long idEvento, String comentario, int puntuacion, Long idParticipante) throws Exception {
        gestionEventoServicio.agregarEvaluacion(idEvento, comentario, puntuacion, idParticipante);
    }

    public void agregarParticipante(Long idEvento, Long idParticipante) throws Exception {
        gestionEventoServicio.agregarParticipante(idEvento, idParticipante);
    }

    public void eliminarParticipante(Long idEvento, Long idParticipante) throws Exception {
        gestionEventoServicio.eliminarParticipante(idEvento, idParticipante);
    }

    public void iniciarEvento(Long idEvento) throws Exception {
        gestionEventoServicio.iniciarEvento(idEvento);
    }

    public void finalizarEvento(Long idEvento) throws Exception {
        gestionEventoServicio.finalizarEvento(idEvento);
    }

}
