package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model;



import com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model.Interes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
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
    //@Fetch(FetchMode.JOIN)
    private Set<Interes> intereses = new HashSet<>();

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    //@Fetch(FetchMode.JOIN)
    private List<Participante> participantes = new ArrayList<>();

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    //@Fetch(FetchMode.JOIN)
    private List<Evaluacion> evaluaciones = new ArrayList<>();
}
