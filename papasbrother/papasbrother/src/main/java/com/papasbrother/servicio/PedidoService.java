package com.papasbrother.servicio;

import com.papasbrother.modelo.Pedido;
import com.papasbrother.modelo.PedidoItem;
import com.papasbrother.repositorio.PedidoRepository;
import com.papasbrother.component.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Timestamp;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import org.thymeleaf.context.Context;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepo;

    @Transactional
    public Pedido createFromCart(Cart cart) {
        Pedido pedido = new Pedido();
        pedido.setFecha(new Timestamp(System.currentTimeMillis()));

        // Por cada elemento del carrito, creamos un PedidoItem con los datos del
        // producto
        cart.getItems().forEach(ci -> {
            PedidoItem pi = new PedidoItem();
            // Aquí copiamos el producto (que ya trae nombre y precio de la BD)
            pi.setProducto(ci.getProducto());
            pi.setCantidad(ci.getCantidad());
            pi.setSubtotal(ci.getSubtotal());
            pi.setPedido(pedido);
            pedido.getItems().add(pi);
        });

        pedido.setTotal(cart.getTotal());
        return pedidoRepo.save(pedido);
    }

    public ByteArrayInputStream generateInvoice(Pedido pedido) {

        // Renderiza la plantilla Thymeleaf a una cadena HTML
        String html = renderBoletaHtml(pedido);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();

            // Si tienes recursos (imágenes, CSS) asegúrate de establecer un "base URI"
            String baseUri = "file:///" + new File("src/main/resources/static").getAbsolutePath() + "/";
            builder.withHtmlContent(html, baseUri);

            builder.toStream(os);
            builder.run();

            return new ByteArrayInputStream(os.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generando el PDF", e);
        }
    }

    @Autowired
    private SpringTemplateEngine templateEngine;

    public String renderBoletaHtml(Pedido pedido) {
        Context context = new Context();
        context.setVariable("cart", pedido);
        // Evita que cartTotal sea nulo
        context.setVariable("cartTotal", (pedido.getTotal() != null ? pedido.getTotal() : java.math.BigDecimal.ZERO));
        return templateEngine.process("cart", context);
    }

}