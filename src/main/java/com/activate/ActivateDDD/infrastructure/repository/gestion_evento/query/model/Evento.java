package com.activate.ActivateDDD.infrastructure.repository.gestion_evento.query.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Document("eventos")
@Data
public class Evento {
    @Id
    private String id;
    private int aforoMaximo;
    private int duracion;//En minutos
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private Ubicacion ubicacion;
    private Estado estado;
    private TipoEvento tipo;
    private Organizador organizador;
    private Set<Interes> intereses;
    private List<Participante> participantes;
    private List<Evaluacion> evaluaciones;
}
