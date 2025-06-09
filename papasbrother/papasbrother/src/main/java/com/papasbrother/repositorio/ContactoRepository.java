package com.papasbrother.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.papasbrother.modelo.Contacto;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> { 

}