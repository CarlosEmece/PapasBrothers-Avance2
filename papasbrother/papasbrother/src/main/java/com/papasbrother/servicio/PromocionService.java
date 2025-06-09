package com.papasbrother.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papasbrother.modelo.Promocion;
import com.papasbrother.repositorio.PromocionRepository;



@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;
    
    public Promocion savePromocion(Promocion promocion) {
        return promocionRepository.save(promocion);
    }
    
    public List<Promocion> getAllPromociones() {
        return promocionRepository.findAll();
    }
    
    public Promocion getPromocionById(Long id) {
        return promocionRepository.findById(id).orElse(null);
    }
    
    public void deletePromocion(Long id) {
        promocionRepository.deleteById(id);
    }
}