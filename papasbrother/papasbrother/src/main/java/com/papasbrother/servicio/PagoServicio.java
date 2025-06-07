package com.papasbrother.servicio;

import com.papasbrother.modelo.Pago;
import com.papasbrother.repositorio.PagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoServicio {
    
    @Autowired
    private PagoRepositorio pagoRepository;

    public List<Pago> listarTodos() {
        return pagoRepository.findAll();
    }

    public void guardar(Pago pago) {
        pagoRepository.save(pago);
    }

    public Optional<Pago> obtenerPorId(Integer id) {
        return pagoRepository.findById(id);
    }

    public void eliminar(Integer id) {
        pagoRepository.deleteById(id);
    }
    
}
