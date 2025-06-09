package com.papasbrother.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.papasbrother.servicio.ContactoService;
import com.papasbrother.servicio.PromocionService;
import com.papasbrother.servicio.SugerenciaService;
import com.papasbrother.servicio.UsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SugerenciaService sugerenciaService;
    
    //Los servicios
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PromocionService promocionService;

    @Autowired
    private ContactoService contactoService;

    @GetMapping
    public String mostrarPanelAdmin(Model model) {
        model.addAttribute("sugerencias", sugerenciaService.getAllSugerencias());
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        model.addAttribute("promociones", promocionService.getAllPromociones());
        model.addAttribute("contactos", contactoService.getAllContactos());
        return "administradorpanel";
    }
}
