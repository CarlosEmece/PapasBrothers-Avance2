package com.papasbrother.controlador;



import com.papasbrother.modelo.Pedido;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.SessionAttribute;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.io.OutputStream;

@Controller
public class CheckoutController {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @GetMapping("/checkout")
    public void checkout(HttpServletResponse response, @SessionAttribute("cart") Pedido pedido) throws IOException {
        // Renderizar HTML de boleta.html usando Thymeleaf
        Context context = new Context();
        context.setVariable("cart", pedido);
        String html = templateEngine.process("boleta", context); // Este archivo debe existir en templates/

        // Configurar la respuesta HTTP para enviar un PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=boleta.pdf");

        // Convertir HTML a PDF y escribirlo en la respuesta
        try (OutputStream os = response.getOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
