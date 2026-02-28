package com.BackBasiFit.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import com.BackBasiFit.enums.EstadoClase;
import com.BackBasiFit.enums.Nivel;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "clases")
public class Clases {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 60)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Nivel nivel = Nivel.principiante;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate fecha;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "hora_final", nullable = false)
    private LocalTime horaFinal;

    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    @Column(nullable = false)
    private Integer capacidad = 20;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoClase estado = EstadoClase.programada;

    @Column(name = "sala_id", nullable = false, length = 36)
    private String salaId;

    @Column(name = "entrenador_id", nullable = false, length = 36)
    private String entrenadorId;

    @Column(name = "gimnasio_id", nullable = false, length = 36)
    private String gimnasioId;

    @Column(name = "fecha_creacion", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
        if (this.duracionMinutos == null) {
            this.duracionMinutos = calcularDuracion();
        }
    }

    private int calcularDuracion() {
        if (horaInicio == null || horaFinal == null) return 0;
        return (int) Duration.between(horaInicio, horaFinal).toMinutes();
    }

    public void recalcularDuracionSiProcede() {
        this.duracionMinutos = calcularDuracion();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(LocalTime horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoClase getEstado() {
        return estado;
    }

    public void setEstado(EstadoClase estado) {
        this.estado = estado;
    }

    public String getSalaId() {
        return salaId;
    }

    public void setSalaId(String salaId) {
        this.salaId = salaId;
    }

    public String getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(String entrenadorId) {
        this.entrenadorId = entrenadorId;
    }

    public String getGimnasioId() {
        return gimnasioId;
    }

    public void setGimnasioId(String gimnasioId) {
        this.gimnasioId = gimnasioId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
