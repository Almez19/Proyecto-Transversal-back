package com.BackBasiFit.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rutinas")
public class Rutinas {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "cliente_id_ru", nullable = false, length = 36)
    private String clienteId;

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

    public String getClienteId() { 
        return clienteId; 
    }

    public void setClienteId(String clienteId) { 
        this.clienteId = clienteId; 
    }
}
