package com.BackBasiFit.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "maquinas")
public class Maquinas {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "gimnasios_id", nullable = false, length = 36)
    private String gimnasioId;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "urlImagen", columnDefinition = "TEXT")
    private String urlImagen;

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

    public String getGimnasioId() { 
        return gimnasioId; 
    }

    public void setGimnasioId(String gimnasioId) { 
        this.gimnasioId = gimnasioId; 
    }

    public String getDescripcion() { 
        return descripcion; 
    }

    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }

    public String getUrlImagen() { 
        return urlImagen; 
    }
    
    public void setUrlImagen(String urlImagen) { 
        this.urlImagen = urlImagen; 
    }
}
