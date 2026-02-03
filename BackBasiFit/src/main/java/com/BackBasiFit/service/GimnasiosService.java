package com.BackBasiFit.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Gimnasios;
import com.BackBasiFit.repository.GimnasiosRepository;

@Service
public class GimnasiosService {
    private final GimnasiosRepository repo;

    public GimnasiosService(GimnasiosRepository repo) { 
        this.repo = repo; 
    }

    public List<Gimnasios> findAll() { 
        return repo.findAll(); 
    }

    public Gimnasios findById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("El Gimnasio exite"));
    }

    public Gimnasios save(Gimnasios g) { 
        return repo.save(g); 
    }

    public void delete(String id) { 
        repo.deleteById(id); 
    }
}
