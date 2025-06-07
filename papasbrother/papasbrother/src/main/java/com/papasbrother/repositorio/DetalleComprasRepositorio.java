package com.papasbrother.repositorio;

import com.papasbrother.modelo.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleComprasRepositorio extends JpaRepository<DetalleCompra, Integer> {
}