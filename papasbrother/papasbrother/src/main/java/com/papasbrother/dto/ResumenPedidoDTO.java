package com.papasbrother.dto;

import java.util.List;

public class ResumenPedidoDTO {
    private List<String> productos;
    private double total;

    public ResumenPedidoDTO(List<String> productos, double total) {
        this.productos = productos;
        this.total = total;
    }

    public List<String> getProductos() {
        return productos;
    }

    public void setProductos(List<String> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
