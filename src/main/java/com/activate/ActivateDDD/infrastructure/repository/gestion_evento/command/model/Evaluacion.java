package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model;

import com.activate.ActivateDDD.domain.gestion_evento.modelo.Participante;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;

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
    @ToString.Exclude
    private EventoCommand evento;

    @PrePersist
    public void prePersist() {
        if (evento != null) {
            evento.setLastModifiedDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (evento != null) {
            evento.setLastModifiedDate(LocalDateTime.now());
        }
    }

    @PreRemove
    public void preRemove() {
        if (evento != null) {
            evento.setLastModifiedDate(LocalDateTime.now());
        }
    }

}
