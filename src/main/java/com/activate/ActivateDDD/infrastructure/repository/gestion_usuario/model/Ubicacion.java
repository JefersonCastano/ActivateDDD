package com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Ubicacion {
    private double latitud;
    private double longitud;
}
