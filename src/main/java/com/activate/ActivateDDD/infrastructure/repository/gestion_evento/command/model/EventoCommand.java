package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model;

import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Interes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class EventoCommand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int aforoMaximo;
    private int duracion;//En minutos
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;

    @Embedded
    private Ubicacion ubicacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipo;

    @Column(name = "organizador_id")
    private Long organizador;

    @ElementCollection(targetClass = Interes.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "evento_intereses", joinColumns = @JoinColumn(name = "evento_id"))
    @Column(name = "interes")
    private Set<Interes> intereses;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participante> participantes ;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluacion> evaluaciones ;
}
