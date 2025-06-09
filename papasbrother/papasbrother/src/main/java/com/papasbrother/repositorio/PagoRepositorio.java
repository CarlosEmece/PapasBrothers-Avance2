package com.papasbrother.repositorio;

import com.papasbrother.modelo.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepositorio extends JpaRepository<Pago, Integer> {
}
