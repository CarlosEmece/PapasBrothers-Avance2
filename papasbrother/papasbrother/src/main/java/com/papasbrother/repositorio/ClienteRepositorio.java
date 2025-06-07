package com.papasbrother.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.papasbrother.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
}
