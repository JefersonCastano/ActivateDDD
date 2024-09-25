package com.activate.ActivateDDD.domain.gestion_evento.modelo;

import com.activate.ActivateDDD.domain.commons.*;
import lombok.Data;
import lombok.Getter;
import org.aspectj.weaver.IClassFileProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class Evento {
    @Getter
    private Long id;
    @Getter
    private int aforoMaximo;
    @Getter
    private int duracion;//En minutos
    @Getter
    private String nombre;
    @Getter
    private String descripcion;
    @Getter
    private LocalDateTime fecha;
    @Getter
    private Ubicacion ubicacion;
    @Getter
    private Estado estado;
    @Getter
    private TipoEvento tipo;
    private Organizador organizador;
    @Getter
    private HashSet<Interes> intereses;
    @Getter
    private ArrayList<Participante> participantes;
    @Getter
    private ArrayList<Evaluacion> evaluaciones;

    public Evento (Long id, int aforoMaximo, int duracion, String nombre, String descripcion, LocalDateTime fecha, Ubicacion ubicacion, TipoEvento tipo, Organizador organizador, HashSet<Interes> intereses) {
        this(id, aforoMaximo, duracion, nombre, descripcion, fecha, ubicacion, Estado.ABIERTO, tipo, organizador, intereses);
    }

    public Evento(Long id, int aforoMaximo, int duracion, String nombre, String descripcion, LocalDateTime fecha, Ubicacion ubicacion, Estado estado,TipoEvento tipo, Organizador organizador, HashSet<Interes> intereses) {
        if(duracion <= 0)
            throw new RuntimeException("La duración del evento debe ser mayor a 0");
        if(duracion > 1440)
            throw new RuntimeException("La duración del evento no puede ser mayor a 1 dia");
        if(aforoMaximo <= 0)
            throw new RuntimeException("El aforo máximo del evento debe ser mayor a 0");
        if(ubicacion == null)
            throw new RuntimeException("La ubicación del evento no puede ser nula");

        if(intereses == null || intereses.isEmpty())
            throw new RuntimeException("El evento debe tener al menos un interés");

        this.id = id;
        this.aforoMaximo = aforoMaximo;
        this.duracion = duracion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.tipo = tipo;
        this.organizador = organizador;
        this.intereses = intereses;
        this.participantes = new ArrayList<>();
        this.evaluaciones = new ArrayList<>();
    }


    public void cancelar() {
        if(this.estado.equals(Estado.FINALIZADO))
            throw new RuntimeException("No se puede cancelar un evento que ya ha finalizado");
        this.estado = Estado.CANCELADO;
    }
    private void cerrar() {
        if(!this.estado.equals(Estado.ABIERTO))
            throw new RuntimeException("No se puede cerrar un evento que no está abierto");
        this.estado = Estado.CERRADO;
    }
    private void reabrir() {
        if(!this.estado.equals(Estado.CERRADO))
            throw new RuntimeException("No se puede reabrir un evento que no está cerrado");
        this.estado = Estado.ABIERTO;
    }
    public void finalizar() {
        if(!this.estado.equals(Estado.EN_PROCESO))
            throw new RuntimeException("No se puede finalizar un evento que no está en proceso");
        this.estado = Estado.FINALIZADO;
    }

    public void iniciar() {
        if(!(this.estado.equals(Estado.ABIERTO) || this.estado.equals(Estado.CERRADO)))
            throw new RuntimeException("No se puede iniciar un evento que no está abierto o cerrado");
        this.estado = Estado.EN_PROCESO;
    }

    public void cambiarTipo(){
        if(this.tipo.equals(TipoEvento.PUBLICO))
            this.tipo = TipoEvento.PRIVADO;
        else
            this.tipo = TipoEvento.PUBLICO;
    }

    public boolean actualizarAforoMaximo(int aforoMaximo){
        if(aforoMaximo <= 0)
            throw new RuntimeException("El aforo máximo del evento debe ser mayor a 0");
        if(this.estado.equals(Estado.FINALIZADO))
            throw new RuntimeException("No se puede actualizar el aforo de un evento que ya ha finalizado");
        if(this.estado.equals(Estado.CANCELADO))
            throw new RuntimeException("No se puede actualizar el aforo de un evento que ha sido cancelado");
        if(this.participantes.size() > aforoMaximo)
            throw new RuntimeException("No se puede actualizar el aforo de un evento que ya ha superado el nuevo aforo");
        this.aforoMaximo = aforoMaximo;
        return true;
    }

    public void actualizarFecha(LocalDateTime fecha){
        if(fecha.isBefore(LocalDateTime.now()))
            throw new RuntimeException("La fecha del evento no puede ser anterior a la fecha actual");
        if(this.estado.equals(Estado.FINALIZADO))
            throw new RuntimeException("No se puede actualizar la fecha de un evento que ya ha finalizado");
        if(this.estado.equals(Estado.CANCELADO))
            throw new RuntimeException("No se puede actualizar la fecha de un evento que ha sido cancelado");
        if(this.estado.equals(Estado.EN_PROCESO))
            throw new RuntimeException("No se puede actualizar la fecha de un evento que ya ha iniciado");
        if(fecha.isBefore(LocalDateTime.now().plusHours(12)))
            throw new RuntimeException("No se puede actualizar la fecha del evento a menos de doce horas de su inicio");
        this.fecha = fecha;
    }

    public void agregarEvaluacion(Evaluacion evaluacion){
        if(!this.estado.equals(Estado.FINALIZADO))
            throw new RuntimeException("No se puede agregar una evaluación a un evento que no ha finalizado");
        if (this.participantes.stream().noneMatch(p -> p.getUsuario().getId().equals(evaluacion.getAutor().getUsuario().getId())))
            throw new RuntimeException("El evaluador no es un participante del evento");

        this.evaluaciones.add(evaluacion);
    }

    public boolean agregarParticipante(Participante participante){
        if(!this.estado.equals(Estado.ABIERTO))
            throw new RuntimeException("No se puede agregar un participante a un evento que no está abierto");
        if(this.participantes.contains(participante))
            throw new RuntimeException("El participante ya está inscrito en el evento");
        if(participantes.stream().anyMatch(p -> p.getUsuario().getId().equals(participante.getUsuario().getId())))
            throw new RuntimeException("El participante ya está inscrito en el evento");
        this.participantes.add(participante);
        if(this.participantes.size() == this.aforoMaximo)
            cerrar();
        return true;
    }

    public boolean eliminarParticipante(Long idParticipante){
        if(!(this.estado.equals(Estado.ABIERTO) || this.estado.equals(Estado.CERRADO)))
            throw new RuntimeException("No se puede eliminar un participante de un evento que no está abierto o cerrado");
        if (participantes.stream().noneMatch(p -> p.getUsuario().getId().equals(idParticipante))) {
            throw new RuntimeException("El participante no está inscrito en el evento");
        }
        participantes.removeIf(p -> p.getUsuario().getId().equals(idParticipante));
        if(this.estado.equals(Estado.CERRADO) && this.participantes.size() < this.aforoMaximo)
            this.reabrir();
        return true;
    }

    public String getNombreOrganizador() {
        return organizador.getNombre();
    }
    public Long getIdOrganizador() {return organizador.getId();}
}