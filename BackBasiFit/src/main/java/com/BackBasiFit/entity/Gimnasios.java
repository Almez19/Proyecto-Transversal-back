package com.BackBasiFit.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gimnasios")
public class Gimnasios {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, unique = true, length = 150)
    private String ubicacion;

    @Column(nullable = false, length = 30)
    private String ciudad;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false)
    private Boolean estado = true;

    public String getId() { 
        return id; 
    }

    public void setId(String id) { 
        this.id = id; 
    }

    public String getUbicacion() { 
        return ubicacion; 
    }

    public void setUbicacion(String ubicacion) { 
        this.ubicacion = ubicacion; 
    }

    public String getCiudad() { 
        return ciudad; 
    }

    public void setCiudad(String ciudad) { 
        this.ciudad = ciudad; 
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
