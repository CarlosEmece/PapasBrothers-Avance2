package com.papasbrother.controlador;

import com.papasbrother.modelo.Pago;
import com.papasbrother.servicio.PagoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/pagos")
public class PagoControlador {

     @Autowired
    private PagoServicio pagoService;

    @GetMapping
    public String listarPagos(Model model) {
        model.addAttribute("pagos", pagoService.listarTodos());
        return "pagos/listar"; // recursos/templates/pagos/listar.html
    }

    @GetMapping("/nuevo")
    public String nuevoPago(Model model) {
        model.addAttribute("pago", new Pago());
        return "pagos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPago(@ModelAttribute Pago pago) {
        pagoService.guardar(pago);
        return "redirect:/pagos";
    }

    @GetMapping("/editar/{id}")
    public String editarPago(@PathVariable Integer id, Model model) {
        Optional<Pago> pago = pagoService.obtenerPorId(id);
        if (pago.isPresent()) {
            model.addAttribute("pago", pago.get());
            return "pagos/formulario";
        }
        return "redirect:/pagos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPago(@PathVariable Integer id) {
        pagoService.eliminar(id);
        return "redirect:/pagos";
    }
    
}
