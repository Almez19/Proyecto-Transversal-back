package com.BackBasiFit.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "noticias")
public class Noticias {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 80)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String cuerpo;

    @Column(name = "url_imagen", nullable = false, columnDefinition = "TEXT")
    private String urlImagen;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(name = "gimnasio_id", length = 36)
    private String gimnasioId;

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getGimnasioId() {
        return gimnasioId;
    }

    public void setGimnasioId(String gimnasioId) {
        this.gimnasioId = gimnasioId;
    }
}
