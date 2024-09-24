package com.activate.ActivateDDD.infrastructure.repository.gestion_usuario.model;


import com.activate.ActivateDDD.infrastructure.repository.gestion_evento.command.model.Participante;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private int edad;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Interes.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "usuario_intereses", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "interes")
    @Fetch(FetchMode.JOIN)
    private Set<Interes> intereses;

    @Embedded
    private Ubicacion ubicacion;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<Participante> participantes;

}