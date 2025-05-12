package com.papasbrother.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaPrincipalControlador {
    @GetMapping("/")
    public String mostrarInicio() {
        return "inicio";
    }

    @GetMapping("/loginn")
    public String mostrarLogin() {
        return "login"; // vista específica para reclamos si lo deseas aparte
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

}
