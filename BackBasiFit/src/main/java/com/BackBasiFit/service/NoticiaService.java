package com.BackBasiFit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BackBasiFit.entity.Noticias;
import com.BackBasiFit.repository.NoticiasRepository;

@Service
public class NoticiaService {
    private final NoticiasRepository noticiasRepository;

    public NoticiaService(NoticiasRepository noticiasRepository) {
        this.noticiasRepository = noticiasRepository;
    }

    public List<Noticias> findAll() { 
        return noticiasRepository.findAll(); 
    }

    public List<Noticias> findTop3ByOrderByFechaDesc() { 
        return noticiasRepository.findTop3ByOrderByFechaDesc(); 
    }
    
    public List<Noticias> findByGimnasioId(String gimnasioId) {
        return noticiasRepository.findByGimnasioId(gimnasioId);
    }

    public Noticias findById(String id) {
        return noticiasRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Esta noticia no existe"));
    }

    public Noticias save(Noticias noticia) { 
        return noticiasRepository.save(noticia); 
    }

    public void delete(String id) { 
        noticiasRepository.deleteById(id); 
    }
}
