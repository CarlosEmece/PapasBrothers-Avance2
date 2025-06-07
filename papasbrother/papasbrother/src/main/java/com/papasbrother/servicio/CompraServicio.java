package com.papasbrother.servicio;

import com.papasbrother.modelo.Compra;
import com.papasbrother.repositorio.ComprasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraServicio {
   
    @Autowired
    private ComprasRepositorio comprasRepository;

    public List<Compra> listarTodos() {
        return comprasRepository.findAll();
    }

    public void guardar(Compra compras) {
        comprasRepository.save(compras);
    }

    public Optional<Compra> obtenerPorId(Integer id) {
        return comprasRepository.findById(id);
    }

    public void eliminar(Integer id) {
        comprasRepository.deleteById(id);
    }
}
