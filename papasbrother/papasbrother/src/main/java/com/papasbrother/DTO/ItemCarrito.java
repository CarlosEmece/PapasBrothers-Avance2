package com.papasbrother.DTO;

import com.papasbrother.modelo.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarrito {
     private Producto producto;
    private int cantidad;
    
}
