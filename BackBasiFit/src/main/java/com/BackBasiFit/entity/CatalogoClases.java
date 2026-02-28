package com.BackBasiFit.entity;

import java.util.UUID;

import com.BackBasiFit.enums.Nivel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "catalogo_clases")
public class CatalogoClases {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, unique = true, length = 60)
    private String nombre;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_recomendado", nullable = false)
    private Nivel nivelRecomendado = Nivel.principiante;

    @Column(name = "url_imagen", columnDefinition = "TEXT")
    private String urlImagen;

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

    public Nivel getNivelRecomendado() {
        return nivelRecomendado;
    }

    public void setNivelRecomendado(Nivel nivelRecomendado) {
        this.nivelRecomendado = nivelRecomendado;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
