package com.papasbrother.controlador;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.papasbrother.modelo.Contacto;
import com.papasbrother.servicio.ContactoService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/contactos")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;
    
    // 1. Mostrar el formulario público de contacto
    @GetMapping("/form")
    public String mostrarFormularioContacto(Model model) {
        model.addAttribute("contacto", new Contacto());
        return "contacto"; 
    }
    
    
    // 2. Procesar el envío del formulario de contacto
    @PostMapping("/enviar")
    public String enviarContacto(@ModelAttribute Contacto contacto) {
        contactoService.saveContacto(contacto);
        return "redirect:/contactos/form?success";
    }
    

    // 3. (Admin) Lista de contactos para su gestión
    @GetMapping
    public String listarContactos(Model model) {
        model.addAttribute("contactos", contactoService.getAllContactos());
        return "administradorpanel"; // Vista para la gestión de contactos en el panel admin
    }
    

    // 4. Actualización vía AJAX 
    @PostMapping("/actualizar")
    @ResponseBody
    public String actualizarContacto(@RequestBody Contacto contacto) {
        Contacto existente = contactoService.getContactoById(contacto.getId());
        if (existente != null) {
            existente.setNombreApellido(contacto.getNombreApellido());
            existente.setCorreo(contacto.getCorreo());
            existente.setTelefono(contacto.getTelefono());
            existente.setMotivo(contacto.getMotivo());
            existente.setDescripcion(contacto.getDescripcion());
            contactoService.saveContacto(existente);
            return "OK";
        }
        return "administradorpanel";
    }
    

    // 5. Eliminación de un contacto
    @GetMapping("/eliminar/{id}")
    public String eliminarContacto(@PathVariable Long id) {
        contactoService.deleteContacto(id);
        return "redirect:/inicio"; 
    }


    // 6. Descargar el excel
    @GetMapping("/exportar")
    public void exportarContactosExcel(HttpServletResponse response) throws IOException {
        // Configurar el tipo de contenido y encabezado para la descarga
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"contactos.xlsx\"");
        
        List<Contacto> listaContactos = contactoService.getAllContactos();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Contactos");
        
        // La fila del encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nombre y Apellido");
        headerRow.createCell(2).setCellValue("Correo");
        headerRow.createCell(3).setCellValue("Teléfono");
        headerRow.createCell(4).setCellValue("Motivo");
        headerRow.createCell(5).setCellValue("Descripción");
        
        int rowNum = 1;
        for (Contacto contacto : listaContactos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(contacto.getId());
            row.createCell(1).setCellValue(contacto.getNombreApellido());
            row.createCell(2).setCellValue(contacto.getCorreo());
            row.createCell(3).setCellValue(contacto.getTelefono());
            row.createCell(4).setCellValue(contacto.getMotivo());
            row.createCell(5).setCellValue(contacto.getDescripcion());
        }
        
        // Para ajustar el ancho de las columnas automáticamente
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }
        
        
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}