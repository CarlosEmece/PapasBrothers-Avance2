package com.papasbrother.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.papasbrother.servicio.ProductoService;

@Controller
public class MenuController {
    @Autowired
    private ProductoService productoService;

    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("productos", productoService.listAll());
        return "menu";
    }
}
