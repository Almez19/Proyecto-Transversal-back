package com.BackBasiFit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BackBasiFit.entity.Noticia;
import com.BackBasiFit.repository.NoticiasRepository;

@Service
public class NoticiaService {
    private final NoticiasRepository repo;

    public NoticiaService(NoticiasRepository repo) {
        this.repo = repo;
    }

    public List<Noticia> findAll() { return repo.findAll(); }

    public Noticia findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Noticia no encontrada"));
    }

    public Noticia save(Noticia n) { return repo.save(n); }
    public void delete(Long id) { repo.deleteById(id); }
}
