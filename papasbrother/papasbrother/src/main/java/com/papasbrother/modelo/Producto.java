package com.papasbrother.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")  // ← necesario

    private Integer idProducto;

    private String nombres;
    private String descripcion;
    private Double precio;
    private Integer stock;

    @Lob
    @Column(name = "foto", columnDefinition = "LONGBLOB") // explícito para MySQL
    private byte[] foto;

    
}
