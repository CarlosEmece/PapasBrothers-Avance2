package com.papasbrother.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.papasbrother.modelo.Administrador;
import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
    Optional<Administrador> findByUsername(String username);
}