package com.BackBasiFit.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.BackBasiFit.entity.CatalogoClases;
import com.BackBasiFit.entity.Clases;
import com.BackBasiFit.repository.CatalogoClasesRepository;
import com.BackBasiFit.repository.ClasesRepository;

@Service
public class ClasesService {

    private final ClasesRepository clasesRepository;
    private final CatalogoClasesRepository catalogoClasesRepository;

    public ClasesService(ClasesRepository clasesRepository, CatalogoClasesRepository catalogoClasesRepository) {
        this.clasesRepository = clasesRepository;
        this.catalogoClasesRepository = catalogoClasesRepository;
    }

    public List<Clases> findAll() {
        return clasesRepository.findAll();
    }

    public Clases findById(String id) {
        return clasesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Esta clase no existe"));
    }

    public List<Clases> findByFecha(LocalDate fecha) {
        return clasesRepository.findByFecha(fecha);
    }

    public List<Clases> findBySala(String salaId) {
        return clasesRepository.findBySalaId(salaId);
    }

    public List<Clases> findBySalaAndFecha(String salaId, LocalDate fecha) {
        return clasesRepository.findBySalaIdAndFecha(salaId, fecha);
    }

    public List<Clases> findByEntrenador(String entrenadorId) {
        return clasesRepository.findByEntrenadorId(entrenadorId);
    }

    public List<Clases> findByGimnasio(String gimnasioId) {
        return clasesRepository.findByGimnasioId(gimnasioId);
    }

    public List<Clases> findByGimnasioAndFecha(String gimnasioId, LocalDate fecha) {
        return clasesRepository.findByGimnasioIdAndFecha(gimnasioId, fecha);
    }

    public List<CatalogoClases> obtenerCatalogoPublico() {
        return catalogoClasesRepository.findByEstadoTrueOrderByNombreAsc();
    }

    public List<String> obtenerNombresCatalogoPorGimnasio(String gimnasioId) {
        return clasesRepository.findNombresDisponiblesPorGimnasio(gimnasioId);
    }

    public Clases save(Clases clase) {
        validarContraCatalogo(clase);

        clase.recalcularDuracionSiProcede();

        return clasesRepository.save(clase);
    }

    public void delete(String id) {
        clasesRepository.deleteById(id);
    }

    private void validarContraCatalogo(Clases clase) {
        if (clase == null || clase.getNombre() == null || clase.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la clase es obligatorio");
        }

        CatalogoClases tipo = catalogoClasesRepository.findByNombreIgnoreCase(clase.getNombre())
                .orElseThrow(() -> new IllegalArgumentException("El tipo de clase no existe en el catálogo"));

        if (Boolean.FALSE.equals(tipo.getEstado())) {
            throw new IllegalArgumentException("El tipo de clase está desactivado en el catálogo");
        }

        if (clase.getDescripcion() == null || clase.getDescripcion().isBlank()) {
            clase.setDescripcion(tipo.getDescripcion());
        }

        if (clase.getNivel() == null) {
            clase.setNivel(tipo.getNivelRecomendado());
        }
    }
}
