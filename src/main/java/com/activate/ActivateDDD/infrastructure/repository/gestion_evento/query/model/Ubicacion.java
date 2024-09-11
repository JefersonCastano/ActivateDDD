package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model;

import lombok.Data;

@Data
public class Ubicacion {
    private double latitud;
    private double longitud;

    public Ubicacion(double v, double v1) {
    }
}
