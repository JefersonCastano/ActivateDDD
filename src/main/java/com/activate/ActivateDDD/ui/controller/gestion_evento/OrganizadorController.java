package com.activate.ActivateDDD.ui.controller.gestion_evento;

import com.activate.ActivateDDD.application.service.gestion_evento.OrganizadorServicio;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;

@RestController
public class OrganizadorController {

    @Autowired
    private OrganizadorServicio organizadorServicio;

    public void crearEvento(int aforoMaximo, int duracion, String nombre, String descripcion, LocalDateTime fecha, Double latitud, Double longitud, TipoEvento tipo, Long idOrganizador, HashSet<Interes> intereses) {
        Long id = 1L;
        Ubicacion ubicacion = new Ubicacion(latitud, longitud);
        organizadorServicio.crearEvento(id, aforoMaximo, duracion, nombre, descripcion, fecha, ubicacion, tipo, idOrganizador, intereses);
    }

    public void cancelarEvento(Long idEvento, Long idOrganizador) {
        organizadorServicio.cancelarEvento(idEvento, idOrganizador);
    }
}
