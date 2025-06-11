package com.papasbrother.controlador;

import com.papasbrother.component.Cart;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    // Inyecta el Cart (con alcance de sesión) en cada modelo
    @ModelAttribute("cart")
    public Cart globalCart(Cart cart) {
        return cart;
    }
}