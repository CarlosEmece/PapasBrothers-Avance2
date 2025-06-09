package com.papasbrother.repositorio;

import com.papasbrother.modelo.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComprasRepositorio extends JpaRepository<Compra, Integer> {
}