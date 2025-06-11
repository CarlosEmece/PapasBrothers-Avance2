package com.papasbrother.controlador;

import com.papasbrother.modelo.Cliente;
import com.papasbrother.modelo.Compra;
import com.papasbrother.modelo.DetalleCompra;
import com.papasbrother.modelo.Pago;
import com.papasbrother.modelo.Producto;
import com.papasbrother.servicio.ClienteServicio;
import com.papasbrother.servicio.CompraServicio;
import com.papasbrother.servicio.DetalleCompraServicio;
import com.papasbrother.servicio.PagoServicio;
import com.papasbrother.servicio.ProductoServicio;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/compra")
public class CompraControlador {

    
    @Autowired
    private CompraServicio comprasService;

    @Autowired
    private ClienteServicio clienteService;

    @Autowired
    private PagoServicio pagoService;

    @Autowired
    private ProductoServicio productoService;

    @Autowired
    private DetalleCompraServicio detalleCompraService;

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


    // -- FUNCIONALIDAD DE CARRITO EN SESIÓN --

    @PostMapping("/agregarAlCarrito")
    public String agregarAlCarrito(@RequestParam Integer idProducto,
                                   @RequestParam Integer cantidad,
                                   HttpSession session) {
        Optional<Producto> optProducto = productoService.buscarPorId(idProducto);
        if (optProducto.isEmpty()) {
            return "redirect:/productos"; // o manejar error
        }
        Producto producto = optProducto.get();

        List<DetalleCompra> carrito = (List<DetalleCompra>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        // Actualizar cantidad si producto ya está en carrito
        boolean existe = false;
        for (DetalleCompra detalle : carrito) {
            if (detalle.getProducto().getIdProducto().equals(idProducto)) {
                detalle.setCantidad(detalle.getCantidad() + cantidad);
                detalle.setPrecioCompra(producto.getPrecio() * detalle.getCantidad());
                existe = true;
                break;
            }
        }
        if (!existe) {
            DetalleCompra nuevoDetalle = new DetalleCompra();
            nuevoDetalle.setProducto(producto);
            nuevoDetalle.setCantidad(cantidad);
            nuevoDetalle.setPrecioCompra(producto.getPrecio() * cantidad);
            carrito.add(nuevoDetalle);
        }

        session.setAttribute("carrito", carrito);

        return "redirect:/compras/carrito";
    }

    @GetMapping("/carrito")
    public String mostrarCarrito(HttpSession session, Model model) {
        List<DetalleCompra> carrito = (List<DetalleCompra>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        double total = carrito.stream().mapToDouble(DetalleCompra::getPrecioCompra).sum();

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);

        return "compras/carrito"; // vista Thymeleaf que debes crear
    }

    @GetMapping("/carrito/eliminar/{idProducto}")
    public String eliminarDelCarrito(@PathVariable Integer idProducto, HttpSession session) {
        List<DetalleCompra> carrito = (List<DetalleCompra>) session.getAttribute("carrito");
        if (carrito != null) {
            carrito.removeIf(det -> det.getProducto().getIdProducto().equals(idProducto));
            session.setAttribute("carrito", carrito);
        }
        return "redirect:/compras/carrito";
    }

    @PostMapping("/finalizarCompra")
    public String finalizarCompra(HttpSession session) {
        List<DetalleCompra> carrito = (List<DetalleCompra>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/compras/carrito";
        }

        // Aquí debes obtener el cliente (en este ejemplo tomo el primero, pero debes adaptar a tu lógica)
        Cliente cliente = clienteService.listarTodos().get(0);

        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setFechaCompra(LocalDateTime.now());
        compra.setEstado("PENDIENTE");

        double montoTotal = carrito.stream().mapToDouble(DetalleCompra::getPrecioCompra).sum();
        compra.setMonto(montoTotal);

        comprasService.guardar(compra);

        for (DetalleCompra detalle : carrito) {
            detalle.setCompra(compra);
            detalleCompraService.guardar(detalle);
        }

        session.removeAttribute("carrito");

        return "redirect:/compras";
    }
}
