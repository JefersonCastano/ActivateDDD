package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.commons.Estado;
import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.TipoEvento;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;

@Getter
public class EventoInfo {
    private Long id;
    private int aforoMaximo;
    private int duracion;//En minutos
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private Ubicacion ubicacion;
    private Estado estado;
    private TipoEvento tipo;
    private String nombreOrganizador;
    private HashSet<Interes> intereses;

    public EventoInfo(Long id, int aforoMaximo, int duracion, String nombre, String descripcion, LocalDateTime fecha, Ubicacion ubicacion, Estado estado, TipoEvento tipo, String nombreOrganizador, HashSet<Interes> intereses) {
        this.id = id;
        this.aforoMaximo = aforoMaximo;
        this.duracion = duracion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.tipo = tipo;
        this.nombreOrganizador = nombreOrganizador;
        this.intereses = intereses;
    }
}
