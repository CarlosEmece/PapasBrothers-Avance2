package com.papasbrother.servicio;


import com.papasbrother.modelo.Promocion;
import com.papasbrother.repositorio.PromocionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PromocionServiceTest {

    @Mock
    private PromocionRepository promocionRepository;

    @InjectMocks
    private PromocionService promocionService;

    private Promocion promocionEjemplo;

    @BeforeEach
    void setUp() {
        promocionEjemplo = new Promocion(1L, "Promo Midweek", "2x1 en hamburguesas", 15.90, "/img/promo1.jpeg");
        System.out.println("▶ Preparando la promoción de prueba: " + promocionEjemplo.getNombre());
    }

    @Test
    void testGuardarPromocion() {
        when(promocionRepository.save(promocionEjemplo)).thenReturn(promocionEjemplo);

        Promocion guardada = promocionService.savePromocion(promocionEjemplo);

        System.out.println("✅ Probando método: savePromocion");
        assertNotNull(guardada);
        assertEquals("Promo Midweek", guardada.getNombre());
        System.out.println("✔ Promoción guardada exitosamente con nombre: " + guardada.getNombre());

        verify(promocionRepository, times(1)).save(promocionEjemplo);
    }

    @Test
    void testListarPromociones() {
        List<Promocion> lista = new ArrayList<>();
        lista.add(promocionEjemplo);

        when(promocionRepository.findAll()).thenReturn(lista);

        List<Promocion> resultado = promocionService.getAllPromociones();

        System.out.println("✅ Probando método: getAllPromociones");
        assertEquals(1, resultado.size());
        System.out.println("✔ Número de promociones recuperadas: " + resultado.size());
        System.out.println("🛎 Primera promoción recuperada: " + resultado.get(0).getNombre());
    }

    @Test
    void testEliminarPromocion() {
        Long promoId = 1L;

        doNothing().when(promocionRepository).deleteById(promoId);
        promocionService.deletePromocion(promoId);

        System.out.println("✅ Probando método: deletePromocion con ID " + promoId);
        verify(promocionRepository, times(1)).deleteById(promoId);
        System.out.println("🗑 Promoción eliminada correctamente.");
    }
}