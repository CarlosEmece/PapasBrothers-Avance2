package com.papasbrother.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.papasbrother.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
    // Buscar cliente por email
    Optional<Cliente> findByEmail(String email);
}
