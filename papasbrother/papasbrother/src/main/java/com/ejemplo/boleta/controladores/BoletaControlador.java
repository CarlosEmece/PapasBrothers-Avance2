package com.ejemplo.boleta.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class BoletaControlador {
    
    //Datos de boleta
    @GetMapping("/boleta")
    public String mostrarBoleta(Model model) {
        model.addAttribute("cliente", "Juan Pérez");
        model.addAttribute("mesa", "4");

        LocalDateTime ahora = LocalDateTime.now();
        model.addAttribute("fecha", ahora.toLocalDate().toString());
        model.addAttribute("hora", ahora.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));

        /// Datos de ejemplo, podrías pasar una lista de productos más adelante
        return "boleta";
    }
}
