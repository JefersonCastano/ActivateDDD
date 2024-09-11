package com.activate.ActivateDDD.domain.commons;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ubicacion {
    private double latitud;
    private double longitud;
}
