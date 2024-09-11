package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Evaluacion {
    private Long id;
    private String comentario;
    private int puntuacion;
    private String autor;
}
