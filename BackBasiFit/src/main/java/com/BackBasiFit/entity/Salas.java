package com.BackBasiFit.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "salas")
public class Salas {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "numero_sala", nullable = false)
    private Integer numeroSala;

    @Column(name = "gimnasio_id", nullable = false, length = 36)
    private String gimnasioId;

    @Column(length = 60)
    private String nombre;

    @Column(nullable = false)
    private Boolean estado = true;

    @PrePersist
    public void prePersist() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(Integer numeroSala) {
        this.numeroSala = numeroSala;
    }

    public String getGimnasioId() {
        return gimnasioId;
    }

    public void setGimnasioId(String gimnasioId) {
        this.gimnasioId = gimnasioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
