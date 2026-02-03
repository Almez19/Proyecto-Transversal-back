package com.BackBasiFit.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Clientes;
import com.BackBasiFit.repository.ClientesRepository;

@Service
public class ClientesService {
    private final ClientesRepository repo;

    public ClientesService(ClientesRepository repo) { 
        this.repo = repo; 
    }

    public List<Clientes> findAll() { 
        return repo.findAll(); 
    }

    public Clientes findById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Este cliente no se existe"));
    }

    public Clientes save(Clientes c) { 
        return repo.save(c); 
    }

    public void delete(String id) { 
        repo.deleteById(id); 
    }
}
