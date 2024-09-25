package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluacion {
    private Long id;
    private String comentario;
    private int puntuacion;
    private String autor;
    private Long idAutor;
}
