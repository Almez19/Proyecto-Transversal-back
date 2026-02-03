package com.BackBasiFit.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Maquinas;
import com.BackBasiFit.repository.MaquinasRepository;

@Service
public class MaquinasService {
    private final MaquinasRepository repo;

    public MaquinasService(MaquinasRepository repo) { 
        this.repo = repo; 
    }

    public List<Maquinas> findAll() { 
        return repo.findAll(); 
    }

    public Maquinas findById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No se a podido encontrar esta maquina"));
    }

    public List<Maquinas> findByGimnasio(String gimnasioId) { 
        return repo.findByGimnasiosId(gimnasioId); 
    }

    public Maquinas save(Maquinas m) { 
        return repo.save(m); 
    }

    public void delete(String id) { 
        repo.deleteById(id); 
    }
}
