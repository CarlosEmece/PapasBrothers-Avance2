package com.papasbrother.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.papasbrother.component.Cart;
import com.papasbrother.component.CartItem;
import com.papasbrother.modelo.Producto;
import com.papasbrother.servicio.ProductoService;


@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired private Cart cart;
    @Autowired private ProductoService productoService;

    @GetMapping("/add/{id}")
    public String add(@PathVariable Long id) {
        Producto producto = productoService.get(id);
    cart.add(producto);
    // Depuración: Imprime en la consola la cantidad de ítems después de la adición
    System.out.println("Carrito ahora tiene: " + cart.getItems().size() + " items");
    for (CartItem item : cart.getItems()) {
         System.out.println("Producto: " + item.getProducto().getNombre() + ", Cantidad: " + item.getCantidad());
    }
    return "redirect:/inicio";
    }


    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id) {
        cart.remove(id);
        return "redirect:/cart";
    }


    @GetMapping
    public String view(Model model) {
        model.addAttribute("cart", cart);
        model.addAttribute("cartTotal", cart.getTotal());
        return "cart";
    }

    
    @GetMapping("/count")
    public Map<String, Integer> getCartCount() {
        Map<String, Integer> response = new HashMap<>();
        response.put("count", cart.getItems().size());
        return response;

    }
}