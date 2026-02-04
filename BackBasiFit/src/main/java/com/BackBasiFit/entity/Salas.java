package com.BackBasiFit.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "salas")
public class Salas {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    @Column(name = "numero_sala", nullable = false)
    private Integer numeroSala;

    @Column(name = "gimnasio_id_s", nullable = false, length = 36)
    private String gimnasioId;

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
}
