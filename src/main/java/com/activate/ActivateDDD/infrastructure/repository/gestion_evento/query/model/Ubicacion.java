package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ubicacion {
    private double latitud;
    private double longitud;

}
