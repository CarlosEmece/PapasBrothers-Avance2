package com.papasbrother.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "compras")
public class Compra {
     
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCompras;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idPago", nullable = false)
    private Pago pago;

    private LocalDateTime fechaCompras = LocalDateTime.now();

    private Double monto;

    private String estado;
}