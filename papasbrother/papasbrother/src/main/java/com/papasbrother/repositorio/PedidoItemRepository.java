package com.papasbrother.repositorio;

import com.papasbrother.modelo.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> { }
