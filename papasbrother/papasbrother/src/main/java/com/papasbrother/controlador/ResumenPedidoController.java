package com.papasbrother.controlador;

import com.papasbrother.dto.ResumenPedidoDTO;
import com.papasbrother.servicio.PedidoResumenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Arrays;

@Controller
public class ResumenPedidoController {
    @Autowired
    private PedidoResumenService pedidoResumenService;

    @GetMapping("/resumen-pedido")
    public String mostrarResumen(@RequestParam(name = "productos") String productos,
                                 @RequestParam(name = "total") double total,
                                 Model model) {
        // Simula productos separados por coma
        ResumenPedidoDTO resumen = pedidoResumenService.generarResumen(Arrays.asList(productos.split(",")), total);
        model.addAttribute("resumen", resumen);
        return "Resultados/resumenPedido";
    }
}
