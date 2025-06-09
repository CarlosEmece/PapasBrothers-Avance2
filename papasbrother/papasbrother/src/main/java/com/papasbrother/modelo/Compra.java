package com.papasbrother.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "compra")
public class Compra {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCompra;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idPago", nullable = false)
    private Pago pago;

    private LocalDateTime fechaCompras;

    private Double monto;

    private String estado;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCompra> detalles = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        fechaCompras = LocalDateTime.now();
    }
   

}