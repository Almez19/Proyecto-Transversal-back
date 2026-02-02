package com.BackBasiFit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BackBasiFit.entity.Noticias;
import com.BackBasiFit.repository.NoticiasRepository;

@Service
public class NoticiaService {
    private final NoticiasRepository repo;

    public NoticiaService(NoticiasRepository repo) {
        this.repo = repo;
    }

    public List<Noticias> findAll() { return repo.findAll(); }

    public Noticias findById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Noticia no encontrada"));
    }

    public Noticias save(Noticias n) { return repo.save(n); }
    public void delete(String id) { repo.deleteById(id); }
}
