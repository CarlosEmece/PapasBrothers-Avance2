package com.papasbrother.servicio;

import com.papasbrother.Exception.ResourceNotFoundException;
import com.papasbrother.modelo.Producto;
import com.papasbrother.repositorio.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository repo;

    public List<Producto> listAll() {
        return repo.findAll();
    }

    public Producto get(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
    }
}