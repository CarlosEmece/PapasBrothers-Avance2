package com.papasbrother.servicio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papasbrother.modelo.Sugerencia;
import com.papasbrother.repositorio.SugerenciaRepository;

import java.util.List;

@Service
public class SugerenciaService {
    @Autowired
    private SugerenciaRepository sugerenciaRepository;

    public Sugerencia saveSugerencia(Sugerencia sugerencia) {
        return sugerenciaRepository.save(sugerencia);
    }

    public List<Sugerencia> getAllSugerencias() {
        return sugerenciaRepository.findAll();
    }

    public Sugerencia getSugerenciaById(Long id) {
        return sugerenciaRepository.findById(id).orElse(null);
    }

    public Sugerencia updateSugerencia(Sugerencia sugerencia) {
        return sugerenciaRepository.save(sugerencia);
    }

    public void deleteSugerencia(Long id) {
        sugerenciaRepository.deleteById(id);
    }
}
