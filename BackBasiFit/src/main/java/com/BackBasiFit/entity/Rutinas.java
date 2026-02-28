package com.BackBasiFit.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.BackBasiFit.enums.Nivel;
import com.BackBasiFit.enums.ObjetivoRutina;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "rutinas")
public class Rutinas {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 60)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ObjetivoRutina objetivo = ObjetivoRutina.salud;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Nivel nivel = Nivel.principiante;

    @Column(name = "dias_por_semana", nullable = false)
    private Integer diasPorSemana = 3;

    @Column(length = 255)
    private String notas;

    @Column(name = "cliente_id", nullable = false, length = 36)
    private String clienteId;

    @Column(name = "entrenador_id", length = 36)
    private String entrenadorId;

    @Column(name = "fecha_creacion", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    // En BD es NOT NULL. La rellenamos desde la aplicación en persist/update.
    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @PrePersist
    public void prePersist() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
        if (this.fechaActualizacion == null) {
            this.fechaActualizacion = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        // La BD también lo actualiza por DEFAULT, pero así mantenemos coherencia cuando Hibernate envía updates.
        this.fechaActualizacion = LocalDateTime.now();
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

    public ObjetivoRutina getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(ObjetivoRutina objetivo) {
        this.objetivo = objetivo;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Integer getDiasPorSemana() {
        return diasPorSemana;
    }

    public void setDiasPorSemana(Integer diasPorSemana) {
        this.diasPorSemana = diasPorSemana;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(String entrenadorId) {
        this.entrenadorId = entrenadorId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
