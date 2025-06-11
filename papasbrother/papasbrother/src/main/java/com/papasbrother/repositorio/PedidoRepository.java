package com.papasbrother.repositorio;

import com.papasbrother.modelo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> { }