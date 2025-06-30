package com.papasbrother.servicio;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.papasbrother.modelo.Pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;


@Service
public class PDFGeneratorService {

    @Autowired
    private TemplateEngine templateEngine;

    public byte[] generarBoleta(Pedido pedido) throws IOException {
        Context context = new Context();
        context.setVariable("usuario", pedido.getUsuario());
        context.setVariable("items", pedido.getItems());
        context.setVariable("total", pedido.getTotal());
        context.setVariable("fecha", pedido.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        context.setVariable("numero", pedido.getNumero());
        context.setVariable("empresa", "PapasBrother");

        String html = templateEngine.process("boleta", context);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, "");
        builder.toStream(baos);
        builder.useFastMode();
        builder.run();

        return baos.toByteArray();
    }
}
