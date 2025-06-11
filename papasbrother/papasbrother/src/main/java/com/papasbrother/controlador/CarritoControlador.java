    package com.papasbrother.controlador;

    import com.papasbrother.DTO.ItemCarrito;
    import com.papasbrother.modelo.Producto;
    import com.papasbrother.modelo.Cliente;
    import com.papasbrother.modelo.Compra;
    import com.papasbrother.modelo.DetalleCompra;
    import com.papasbrother.modelo.Pago;
    import com.papasbrother.servicio.ProductoServicio;
    import com.papasbrother.servicio.ClienteServicio;
    import com.papasbrother.servicio.CompraServicio;
    import com.papasbrother.servicio.DetalleCompraServicio;
    import com.papasbrother.servicio.PagoServicio;

    import jakarta.servlet.http.HttpSession;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.security.Principal;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    @Controller
    @RequestMapping("/carrito")
    public class CarritoControlador {
        
        @Autowired
        private ProductoServicio productoServicio;

        @Autowired
        private ClienteServicio clienteServicio;

        @Autowired
        private CompraServicio compraServicio;

        @Autowired
        private DetalleCompraServicio detalleCompraServicio;

        @Autowired
        private PagoServicio pagoServicio;

        // Mostrar el carrito
        @GetMapping
        public String verCarrito(HttpSession session, Model model) {
            List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new ArrayList<>();
            }
            model.addAttribute("carrito", carrito);

            double total = carrito.stream()
                    .mapToDouble(i -> i.getCantidad() * i.getProducto().getPrecio())
                    .sum();
            model.addAttribute("total", total);

            return "carrito/ver"; // Aquí debes tener tu vista para mostrar carrito
        }

        // Agregar producto al carrito
        @PostMapping("/agregar")
        public String agregarProducto(@RequestParam Integer idProducto,
                                    @RequestParam int cantidad,
                                    HttpSession session) {
            List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new ArrayList<>();
            }

            Optional<Producto> productoOpt = productoServicio.buscarPorId(idProducto);
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                boolean encontrado = false;
                for (ItemCarrito item : carrito) {
                    if (item.getProducto().getIdProducto().equals(idProducto)) {
                        item.setCantidad(item.getCantidad() + cantidad);
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    carrito.add(new ItemCarrito(producto, cantidad));
                }
            }

            session.setAttribute("carrito", carrito);
            return "redirect:/carrito";
        }

        // Quitar producto del carrito
        @GetMapping("/quitar/{idProducto}")
        public String quitarProducto(@PathVariable Integer idProducto, HttpSession session) {
            List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
            if (carrito != null) {
                carrito.removeIf(item -> item.getProducto().getIdProducto().equals(idProducto));
            }
            session.setAttribute("carrito", carrito);
            return "redirect:/carrito";
        }

        // Confirmar compra y guardar en BD
        @PostMapping("/confirmar")
        public String confirmarCompra(HttpSession session, Principal principal) {
            List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
            if (carrito == null || carrito.isEmpty()) {
                return "redirect:/carrito";
            }

            // Obtener cliente por email del usuario logueado
            Optional<Cliente> clienteOpt = clienteServicio.buscarPorEmail(principal.getName());
            if (clienteOpt.isEmpty()) {
                // Manejar error, usuario no encontrado
                return "redirect:/login"; 
            }
            Cliente cliente = clienteOpt.get();

            // Crear entidad Pago (puedes adaptarlo si quieres otro método de pago)
            Pago pago = new Pago();
            pago.setMonto(carrito.stream().mapToDouble(i -> i.getCantidad() * i.getProducto().getPrecio()).sum());
            pagoServicio.guardar(pago);

            // Crear entidad Compra
            Compra compra = new Compra();
            compra.setCliente(cliente);
            compra.setPago(pago);
            compra.setEstado("PENDIENTE"); // O el estado que uses
            compra.setMonto(pago.getMonto());
            compraServicio.guardar(compra);

            // Guardar detalles de compra
            for (ItemCarrito item : carrito) {
                DetalleCompra detalle = new DetalleCompra();
                detalle.setCompra(compra);
                detalle.setProducto(item.getProducto());
                detalle.setCantidad(item.getCantidad());
                detalle.setPrecioCompra(item.getProducto().getPrecio());
                detalleCompraServicio.guardar(detalle);
            }

            // Limpiar carrito en sesión
            session.removeAttribute("carrito");

            return "redirect:/compras"; // O la página de éxito / historial
        }
        
    }
