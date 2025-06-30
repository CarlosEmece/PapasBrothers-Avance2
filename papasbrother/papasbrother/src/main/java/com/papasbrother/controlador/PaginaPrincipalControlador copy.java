package com.papasbrother.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

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
    

    @GetMapping("/crearcuenta")
    public String crearCuenta() {
        return "crearcuenta";
    }


    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "redirect:/contactos/form";
    }

    @GetMapping("/nosotros")
    public String mostrarNosotros() {
        return "nosotros";
    }

    

    @GetMapping("/terminos")
    public String mostrarTerminos() {
        return "terminos";
    }

    @GetMapping("/politicas")
    public String mostrarPoliticas() {
        return "politicas";
    }

    @GetMapping("/boleta")
    public String mostrarBoleta() {
        return "boleta";
    }
    
   //MENU
    @GetMapping("/menuhambur")
    public String mostrarMenuHamburguesas() {
        return "menuhambur"; 
    }
    

    @GetMapping("/menualit")
    public String mostrarMenuAlitas(Model model) {
        model.addAttribute("titulo", "titulo.menualit");
        return "menualit";
    }

    @GetMapping("/menusalchi")
    public String mostrarMenuSalchipapas() {
        return "menusalchi"; 
    }

    @GetMapping("/menupech")
    public String mostrarMenuPechuga() {
        return "menupech"; 
    }


   
     
}
