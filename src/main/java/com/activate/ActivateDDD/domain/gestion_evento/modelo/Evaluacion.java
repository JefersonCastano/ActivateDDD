package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import lombok.Data;

@Data
public class Evaluacion {
    private Long id;
    private String comentario;
    private int puntuacion;
    private Participante autor;

    public Evaluacion(Long id, String comentario, int puntuacion, Participante autor) {
        if(puntuacion < 0 || puntuacion > 5)
            throw new RuntimeException("La puntuación de la evaluación debe estar entre 0 y 5");
        if(comentario == null || comentario.isEmpty())
            throw new RuntimeException("El comentario de la evaluación no puede ser nulo o vacío");
        if(autor == null)
            throw new RuntimeException("El autor de la evaluación no puede ser nulo");
        this.id = id;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.autor = autor;
    }

}
