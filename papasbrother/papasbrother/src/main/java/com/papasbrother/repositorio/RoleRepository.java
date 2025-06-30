package com.papasbrother.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.papasbrother.modelo.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}