package com.papasbrother.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.papasbrother.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {}