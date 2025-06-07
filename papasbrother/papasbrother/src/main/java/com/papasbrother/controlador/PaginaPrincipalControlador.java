package com.papasbrother.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaPrincipalControlador {
    @GetMapping("/")
    public String mostrarIndex() {
        return "index"; // Cambia "inicio" por "index"
    }

    @GetMapping("/inicio")
    public String mostrarInicio() {
        return "inicio"; // Ahora "inicio" tiene su propia ruta
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


    @GetMapping("/administradorpanel")
    public String mostrarAdministrador() {
        return "administradorpanel"; 
    }
 

    @GetMapping("/sugerencia")
    public String mostrarSugerencia() {
        return "sugerencia"; 
    }

}
