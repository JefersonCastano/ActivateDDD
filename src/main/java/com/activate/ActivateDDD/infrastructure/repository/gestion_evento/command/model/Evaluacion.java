package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model;

import com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comentario;
    private int puntuacion;
    private Long autor;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private EventoCommand evento;

}
