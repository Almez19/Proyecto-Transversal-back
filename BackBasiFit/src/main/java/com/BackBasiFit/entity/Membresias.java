package com.BackBasiFit.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "membresias")
public class Membresias {
    @Id
    
    private String id = UUID.randomUUID().toString();

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable= false)
    String fecha_inicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable= false)
    String fecha_final;

    @Column(nullable= false)
    boolean estado;








}
