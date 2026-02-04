package com.BackBasiFit.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ejercicios")
public class Ejercicios {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "rutina_id_e", nullable = false, length = 36)
    private String rutinaId;

    @Column(name = "maquina_id_e", nullable = false, length = 36)
    private String maquinaId;

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
