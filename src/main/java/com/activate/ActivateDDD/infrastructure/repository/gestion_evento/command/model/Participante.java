package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model;


import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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


}