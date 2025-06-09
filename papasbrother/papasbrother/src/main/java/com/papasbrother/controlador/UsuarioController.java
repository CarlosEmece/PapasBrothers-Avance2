package com.papasbrother.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.papasbrother.modelo.Usuario;
import com.papasbrother.servicio.UsuarioService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Muestra el formulario de registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    
    // 1. Procesa el registro y guarda la cuenta del usuario en la base de datos
    @PostMapping("/registro/enviar")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        Timestamp now = Timestamp.from(Instant.now());
        usuario.setCreatedAt(now);
        usuario.setUpdatedAt(now);
        usuarioService.saveUsuario(usuario);

        return "redirect:/inicio";
    }


    // 2. Actualiza un usuario
    @PostMapping("/usuarios/actualizar")
    @ResponseBody
    public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioService.getUsuarioById(usuario.getId());
        if (usuarioExistente != null) {
            usuarioExistente.setNombreCompleto(usuario.getNombreCompleto());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setUpdatedAt(Timestamp.from(Instant.now()));
            usuarioService.saveUsuario(usuarioExistente);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    // 3. Elimina un usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return "redirect:/inicio";
    }


    // 4. Para listar
    @GetMapping("/administradorpanel")
    public String mostrarAdministrador(Model model) {
        // Cargar la lista de usuarios desde la base de datos
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        System.out.println("Usuarios encontrados: " + usuarios.size());
        model.addAttribute("usuarios", usuarios);
        return "administradorpanel";
    }


    // 5. Para exportar en PDF
    @GetMapping("/exportar")
    public void exportarUsuariosPDF(HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"usuarios.pdf\"");

        // Para obtener la lista de usuarios
        List<Usuario> usuarios = usuarioService.getAllUsuarios();

        // Crea el documento PDF y añade una página (
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.LETTER);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // ===========================
        // Encabezado: Título y Logo
        // ===========================
        float pageWidth = page.getMediaBox().getWidth();
        float headerY = page.getMediaBox().getHeight() - 50;
        int titleFontSize = 20;
        String title = "USUARIOS";

        // Fuente para el título
        PDType1Font titleFont = PDType1Font.HELVETICA_BOLD;
        float textWidth = titleFont.getStringWidth(title) / 1000 * titleFontSize;
        float logoWidthDesired = 60;
        float logoHeightDesired = 60;
        float gap = 10;
        float totalHeaderWidth = textWidth + gap + logoWidthDesired;
        float startX = (pageWidth - totalHeaderWidth) / 2;

        // Dibuja el título "USUARIOS"
        contentStream.beginText();
        contentStream.setFont(titleFont, titleFontSize);
        contentStream.newLineAtOffset(startX, headerY);
        contentStream.showText(title);
        contentStream.endText();

        InputStream in = getClass().getResourceAsStream("/static/img/LogoPapaB.png");
        if (in != null) {
            byte[] logoBytes = org.apache.commons.io.IOUtils.toByteArray(in);
            PDImageXObject logo = PDImageXObject.createFromByteArray(document, logoBytes, "Logo");
            float logoX = startX + textWidth + gap;
            float logoY = headerY - 10;
            contentStream.drawImage(logo, logoX, logoY, logoWidthDesired, logoHeightDesired);
        }

        // ====================================
        // Tabla con la lista de usuarios
        // ====================================
        float margin = 50;
        float tableStartY = headerY - 80;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        float rowHeight = 20;
        float[] colWidths = { 50, 250, 250 };
        int rows = usuarios.size() + 1;
        float tableHeight = rowHeight * rows;

        // Dibuja las líneas horizontales de cada fila
        for (int i = 0; i <= rows; i++) {
            float yPosition = tableStartY - i * rowHeight;
            contentStream.moveTo(margin, yPosition);
            contentStream.lineTo(margin + tableWidth, yPosition);
        }

        // Dibuja las líneas verticales de cada columna
        float nextX = margin;
        for (int i = 0; i < colWidths.length + 1; i++) {
            contentStream.moveTo(nextX, tableStartY);
            contentStream.lineTo(nextX, tableStartY - tableHeight);
            if (i < colWidths.length) {
                nextX += colWidths[i];
            }
        }
        contentStream.stroke();

        // Escribe la fila de encabezado
        float textX = margin + 2;
        float textY = tableStartY - 15;
        String[] headers = { "ID", "Nombre Completo", "Correo" };
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        for (int i = 0; i < headers.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(textX, textY);
            contentStream.showText(headers[i]);
            contentStream.endText();
            textX += colWidths[i];
        }

        // Escribe las filas de datos de cada usuario
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        float currentY = tableStartY - rowHeight;
        for (Usuario user : usuarios) {
            textX = margin + 2;
            textY = currentY - 15;
            String idStr = String.valueOf(user.getId());
            String nombre = user.getNombreCompleto();
            String correo = user.getEmail();
            String[] cells = { idStr, nombre, correo };
            for (int i = 0; i < cells.length; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(textX, textY);
                contentStream.showText(cells[i]);
                contentStream.endText();
                textX += colWidths[i];
            }
            currentY -= rowHeight;
        }

        contentStream.close();
        document.save(response.getOutputStream());
        document.close();
    }
}
