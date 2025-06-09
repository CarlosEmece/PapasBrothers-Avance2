package com.papasbrother.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.papasbrother.modelo.Sugerencia;
import com.papasbrother.servicio.SugerenciaService;

import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/sugerencias")
public class SugerenciaController {
    @Autowired
    private SugerenciaService sugerenciaService;


    
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("sugerencia", new Sugerencia());
        return "administradorpanel/formulario";
    }

    // 2. Para poder enviar las sugerencias 
    @PostMapping("/enviar")
    public String enviarSugerencia(@ModelAttribute Sugerencia sugerencia) {
        sugerencia.setFechaCreacion(new java.util.Date());
        sugerenciaService.saveSugerencia(sugerencia);
        return "redirect:/inicio"; // O bien mantener /inicio si ese es tu flujo
    }


    // 3. Para poder listar
    @GetMapping("/list")
    public String listarSugerencias(Model model) {
        model.addAttribute("sugerencias", sugerenciaService.getAllSugerencias());
        return "redirect:/administradorpanel";
    }


    // 4. Para poder actualizar
    @PostMapping("/actualizar")
    @ResponseBody
    public ResponseEntity<?> actualizarSugerencia(@RequestBody Sugerencia sugerencia) {
        Sugerencia sugerenciaExistente = sugerenciaService.getSugerenciaById(sugerencia.getId());
        if (sugerenciaExistente != null) {
            sugerenciaExistente.setNombre(sugerencia.getNombre());
            sugerenciaExistente.setEmail(sugerencia.getEmail());
            sugerenciaExistente.setDescripcion(sugerencia.getDescripcion());
            sugerenciaService.saveSugerencia(sugerenciaExistente);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // 5. Para poder eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminarSugerencia(@PathVariable Long id, Model model) {
        sugerenciaService.deleteSugerencia(id);
        model.addAttribute("sugerencias", sugerenciaService.getAllSugerencias());
        return "redirect:/inicio";
    }

}
