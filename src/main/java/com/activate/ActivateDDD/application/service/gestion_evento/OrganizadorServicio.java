package com.activate.ActivateDDD.application.service.gestion_evento;

import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class OrganizadorServicio {
    public void crearEvento(Long id, int aforoMaximo, int duracion, String nombre, String descripcion, LocalDateTime fecha, Ubicacion ubicacion, TipoEvento tipo, Long idOrganizador, HashSet<Interes> intereses) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cancelarEvento(Long idEvento, Long idOrganizador) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
