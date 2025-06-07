package com.papasbrother.controlador;

import com.papasbrother.modelo.Cliente;
import com.papasbrother.modelo.Compra;
import com.papasbrother.modelo.Pago;
import com.papasbrother.servicio.ClienteServicio;
import com.papasbrother.servicio.CompraServicio;
import com.papasbrother.servicio.PagoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/compras")
public class CompraControlador {

    @Autowired
    private CompraServicio comprasService;

    @Autowired
    private ClienteServicio clienteService;

    @Autowired
    private PagoServicio pagoService;

    @GetMapping
    public String listarCompras(Model model) {
        List<Compra> lista = comprasService.listarTodos();
        model.addAttribute("compras", lista);
        return "compras/listar";
    }

    @GetMapping("/nuevo")
    public String nuevaCompra(Model model) {
        model.addAttribute("compra", new Compra());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("pagos", pagoService.listarTodos());
        return "compras/formulario";
    }

    @PostMapping("/guardar")
    public String guardarCompra(@ModelAttribute("compra") Compra compra) {
        comprasService.guardar(compra);
        return "redirect:/compras";
    }

    @GetMapping("/editar/{id}")
    public String editarCompra(@PathVariable Integer id, Model model) {
        Optional<Compra> compra = comprasService.obtenerPorId(id);
        if (compra.isPresent()) {
            model.addAttribute("compra", compra.get());
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("pagos", pagoService.listarTodos());
            return "compras/formulario";
        }
        return "redirect:/compras";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCompra(@PathVariable Integer id) {
        comprasService.eliminar(id);
        return "redirect:/compras";
    } 
}
