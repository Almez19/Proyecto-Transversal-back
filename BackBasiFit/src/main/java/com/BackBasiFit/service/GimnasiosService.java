package com.BackBasiFit.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Gimnasios;
import com.BackBasiFit.repository.GimnasiosRepository;

@Service
public class GimnasiosService {
    private final GimnasiosRepository gimnasiosRepository;

    public GimnasiosService(GimnasiosRepository gimnasiosRepository) { 
        this.gimnasiosRepository = gimnasiosRepository; 
    }

    public List<Gimnasios> findAll() { 
        return gimnasiosRepository.findAll(); 
    }

    public Gimnasios findById(String id) {
        return gimnasiosRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El Gimnasio exite"));
    }

    public Gimnasios save(Gimnasios gimnasio) { 
        return gimnasiosRepository.save(gimnasio); 
    }

    public void delete(String id) { 
        gimnasiosRepository.deleteById(id); 
    }
}
