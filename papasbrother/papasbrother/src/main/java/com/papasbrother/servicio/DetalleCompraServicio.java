package com.papasbrother.servicio;

import com.papasbrother.modelo.DetalleCompra;
import com.papasbrother.repositorio.DetalleComprasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleCompraServicio {

   @Autowired
    private DetalleComprasRepositorio detalleCompraRepositorio;

    public List<DetalleCompra> listarTodos() {
        return detalleCompraRepositorio.findAll();
    }

    public Optional<DetalleCompra> buscarPorId(Integer id) {
        return detalleCompraRepositorio.findById(id);
    }

    public DetalleCompra guardar(DetalleCompra detalleCompra) {
        return detalleCompraRepositorio.save(detalleCompra);
    }

    public void eliminar(Integer id) {
        detalleCompraRepositorio.deleteById(id);
    }
}
