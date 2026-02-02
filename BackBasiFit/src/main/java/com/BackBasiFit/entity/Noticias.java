package com.BackBasiFit.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "noticias")
public class Noticias {
    
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable= false, length= 50)
    String titulo;

    @Column(nullable= false)
    String cuerpo;

    @Column(nullable= false)
    String urlInagen;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable= false)
    String fecha;

    @Column(nullable= false, length= 36)
    String gimnasio;

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

    public String getUrlInagen() {
        return urlInagen;
    }

    public void setUrlInagen(String urlInagen) {
        this.urlInagen = urlInagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getGimnasio() {
        return gimnasio;
    }

    public void setGimnasio(String gimnasio) {
        this.gimnasio = gimnasio;
    }
}
