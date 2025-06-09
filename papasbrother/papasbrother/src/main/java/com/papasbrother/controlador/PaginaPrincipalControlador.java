package com.papasbrother.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaPrincipalControlador {
    @GetMapping("/")
    public String mostrarIndex() {
        return "index"; 
    }

    @GetMapping("/inicio")
    public String mostrarInicio() {
        return "inicio"; 
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; 
    }

    @GetMapping("/menu")
    public String mostrarMenu() {
        return "menu"; 
    }

    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "contacto"; 
    }

    @GetMapping("/nosotros")
    public String mostrarNosotros() {
        return "nosotros"; 
    }

    @GetMapping("/promociones")
    public String mostrarPromociones() {
        return "promociones"; 
    }

    @GetMapping("/terminos")
    public String mostrarTerminos() {
        return "terminos"; 
    }

    @GetMapping("/politicas")
    public String mostrarPoliticas() {
        return "politicas"; 
    }


}
