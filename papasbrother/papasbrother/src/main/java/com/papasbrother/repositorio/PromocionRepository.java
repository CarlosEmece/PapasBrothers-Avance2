package com.papasbrother.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.papasbrother.modelo.Promocion;


@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    
 }
