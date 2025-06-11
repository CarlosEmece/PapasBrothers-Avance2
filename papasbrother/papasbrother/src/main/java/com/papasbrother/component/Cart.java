package com.papasbrother.component;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.papasbrother.modelo.Producto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class Cart {

    private final List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public void add(Producto p) {
    items.stream()
         .filter(i -> i.getProducto().getId().equals(p.getId()))
         .findFirst()
         .ifPresentOrElse(
             i -> {
                 i.setCantidad(i.getCantidad() + 1);
                 System.out.println("Producto repetido, cantidad: " + i.getCantidad());
             },
             () -> {
                 items.add(new CartItem(p, 1));
                 System.out.println("Producto agregado, nuevo item.");
             }
         );
    }
    
    public BigDecimal getTotal() {
        return items.stream()
                    .map(CartItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void remove(Long productId) {
        items.removeIf(i -> i.getProducto().getId().equals(productId));
    }

    public void clear() {
        items.clear();
    }
}