package com.papasbrother.controlador;

import com.papasbrother.modelo.Producto;
import com.papasbrother.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoControlador {
    @Autowired
    private ProductoServicio productoService;

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "productos/listar"; // resources/templates/productos/listar.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/formulario"; // resources/templates/productos/formulario.html
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Optional<Producto> producto = productoService.buscarPorId(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "productos/formulario";
        }
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        productoService.eliminar(id);
        return "redirect:/productos";
    }
    
}
