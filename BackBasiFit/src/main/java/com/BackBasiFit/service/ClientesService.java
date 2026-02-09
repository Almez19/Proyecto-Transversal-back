package com.BackBasiFit.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Clientes;
import com.BackBasiFit.entity.Gimnasios;
import com.BackBasiFit.repository.ClientesRepository;

@Service
public class ClientesService {
    private final ClientesRepository clientesRepository;

    public ClientesService(ClientesRepository clientesRepository) { 
        this.clientesRepository = clientesRepository; 
    }

    public List<Clientes> findAll() { 
        return clientesRepository.findAll(); 
    }

    public Clientes findById(String id) {
        return clientesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Este cliente no se existe"));
    }

    public Clientes save(Clientes cliente) { 
        return clientesRepository.save(cliente); 
    }

    public void delete(String id) { 
        clientesRepository.deleteById(id); 
    }

    public List<Clientes> findTop5ByOrderByEstadoDesc() { 
        return clientesRepository.findTop5ByOrderByEstadoDesc(); 
    }
}
