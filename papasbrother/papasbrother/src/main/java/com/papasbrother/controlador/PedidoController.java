package com.papasbrother.controlador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.papasbrother.dto.ItemCarrito;
import com.papasbrother.modelo.Pedido;
import com.papasbrother.servicio.PDFGeneratorService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;



@Controller
public class PedidoController {

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @GetMapping("/boleta/pdf")
    public void generarBoleta(HttpServletResponse response,
                              @AuthenticationPrincipal UserDetails userDetails,
                              HttpSession session) throws Exception {
        // Recupera los ítems del carrito almacenados en sesión (asegúrate de haberlos guardado allí)
        @SuppressWarnings("unchecked")
        List<ItemCarrito> items = (List<ItemCarrito>) session.getAttribute("carrito");

        if (items == null || items.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El carrito está vacío");
            return;
        }
        
        // Calcula el total del pedido
        double total = items.stream()
                            .mapToDouble(i -> i.getPrecio() * i.getCantidad())
                            .sum();
        
        // Crea un objeto Pedido con los datos necesarios:
        Pedido pedido = new Pedido();
        pedido.setNumero("PED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        pedido.setFecha(LocalDateTime.now());
        pedido.setUsuario(userDetails.getUsername()); // Obtiene el nombre del usuario autenticado
        pedido.setItems(items);
        pedido.setTotal(total);
        
        // Genera el PDF con la ayuda del servicio PDFGeneratorService
        byte[] pdfBytes = pdfGeneratorService.generarBoleta(pedido);
        
        // Configura la respuesta HTTP para que el navegador lo descargue
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=boleta_" + pedido.getNumero() + ".pdf");
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
        
        // (Opcional) Limpia el carrito de la sesión
        session.removeAttribute("carrito");
    }
}