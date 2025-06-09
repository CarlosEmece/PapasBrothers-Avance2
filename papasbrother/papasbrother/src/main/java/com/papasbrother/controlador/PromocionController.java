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
import com.papasbrother.modelo.Promocion;
import com.papasbrother.servicio.PromocionService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/promociones")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    // 1. Para poder listar las promociones 
    @GetMapping("/promociones")
    public String mostrarPromocionesPublicas(Model model) {
        model.addAttribute("promociones", promocionService.getAllPromociones());
        return "promociones"; 
    }

    // 2. Para poder enviar las promociones 
    @PostMapping("/enviar")
    public String enviarPromocion(@ModelAttribute Promocion promocion) {
        promocionService.savePromocion(promocion);
        return "redirect:/promociones"; 
    }

    // 3. Actualiza una promoción vía AJAX 
    @PostMapping("/actualizar")
    @ResponseBody
    public String actualizarPromocion(@RequestBody Promocion promocion) {
        Promocion existente = promocionService.getPromocionById(promocion.getId());
        if (existente != null) {
            existente.setNombre(promocion.getNombre());
            existente.setDescripcion(promocion.getDescripcion());
            existente.setPrecio(promocion.getPrecio());
            
            promocionService.savePromocion(existente);
            return "OK";
        }
        return "Not Found";
    }


    // 5. Elimina una promoción y redirige a la página de promociones
    @GetMapping("/eliminar/{id}")
    public String eliminarPromocion(@PathVariable Long id) {
        promocionService.deletePromocion(id);
        return "redirect:/promociones";
    }


    // 6. Exportar promociones a Excel con Apache POI
    @GetMapping("/exportar")
    public void exportarPromocionesExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"promociones.xlsx\"");

        List<Promocion> listaPromociones = promocionService.getAllPromociones();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Promociones");

        // Crear encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nombre");
        headerRow.createCell(2).setCellValue("Descripción");
        headerRow.createCell(3).setCellValue("Precio");

        int rowNum = 1;
        for (Promocion promo : listaPromociones) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(promo.getId());
            row.createCell(1).setCellValue(promo.getNombre());
            row.createCell(2).setCellValue(promo.getDescripcion());
            row.createCell(3).setCellValue(promo.getPrecio());
        }

        // Autoajustar columnas
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}