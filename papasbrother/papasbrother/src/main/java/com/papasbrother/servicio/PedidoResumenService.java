package com.papasbrother.servicio;

import com.papasbrother.dto.ResumenPedidoDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PedidoResumenService {
    public ResumenPedidoDTO generarResumen(List<String> productos, double total) {
        return new ResumenPedidoDTO(productos, total);
    }
}
