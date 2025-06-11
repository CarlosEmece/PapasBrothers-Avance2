package com.papasbrother.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.papasbrother.modelo.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> { 

}