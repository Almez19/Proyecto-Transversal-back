package com.BackBasiFit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BackBasiFit.entity.Maquinas;
import com.BackBasiFit.repository.MaquinasRepository;

@Service
public class MaquinasService {
    private final MaquinasRepository maquinasRepository;

    public MaquinasService(MaquinasRepository maquinasRepository) { 
        this.maquinasRepository = maquinasRepository; 
    }

    public List<Maquinas> findAll() { 
        return maquinasRepository.findAll(); 
    }

    public Maquinas findById(String id) {
        return maquinasRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No se a podido encontrar esta maquina"));
    }

    public List<Maquinas> findByGimnasio(String gimnasioId) { 
        return maquinasRepository.findByGimnasioId(gimnasioId); 
    }

    public Maquinas save(Maquinas maquina) { 
        return maquinasRepository.save(maquina); 
    }

    public void delete(String id) { 
        maquinasRepository.deleteById(id); 
    }

     public List<Maquinas> findTop5ByOrderByIdDesc() { 
        return maquinasRepository.findTop5ByOrderByIdDesc(); 
    }}
