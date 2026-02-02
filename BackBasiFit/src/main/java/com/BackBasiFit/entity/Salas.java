package com.BackBasiFit.entity;


import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Salas {
    
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable= false)
    Number numero_sala;

    @Column(nullable= false)
    String gimnasio_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Number getNumero_sala() {
        return numero_sala;
    }

    public void setNumero_sala(Number numero_sala) {
        this.numero_sala = numero_sala;
    }

    public String getGimnasio_id() {
        return gimnasio_id;
    }

    public void setGimnasio_id(String gimnasio_id) {
        this.gimnasio_id = gimnasio_id;
    }

}

