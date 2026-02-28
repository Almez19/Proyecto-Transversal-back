package com.BackBasiFit.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "ejercicios")
public class Ejercicios {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false)
    private Integer orden = 1;

    @Column(nullable = false)
    private Integer series = 3;

    @Column(nullable = false)
    private Integer repeticiones = 10;

    @Column(precision = 6, scale = 2)
    private BigDecimal peso;

    @Column(name = "descanso_segundos", nullable = false)
    private Integer descansoSegundos = 60;

    @Column(length = 255)
    private String notas;

    @Column(name = "rutina_id", nullable = false, length = 36)
    private String rutinaId;

    @Column(name = "maquina_id", length = 36)
    private String maquinaId;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(Integer repeticiones) {
        this.repeticiones = repeticiones;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Integer getDescansoSegundos() {
        return descansoSegundos;
    }

    public void setDescansoSegundos(Integer descansoSegundos) {
        this.descansoSegundos = descansoSegundos;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getRutinaId() {
        return rutinaId;
    }

    public void setRutinaId(String rutinaId) {
        this.rutinaId = rutinaId;
    }

    public String getMaquinaId() {
        return maquinaId;
    }

    public void setMaquinaId(String maquinaId) {
        this.maquinaId = maquinaId;
    }
}
