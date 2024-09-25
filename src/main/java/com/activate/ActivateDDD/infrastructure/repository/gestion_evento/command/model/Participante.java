package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model;


import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Entity
@Table(name="participante")
@AllArgsConstructor
@NoArgsConstructor
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @ToString.Exclude
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "evento_id")
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