package com.papasbrother;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.papasbrother.modelo.Sugerencia;
import com.papasbrother.repositorio.SugerenciaRepository;
import com.papasbrother.servicio.SugerenciaService;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SugerenciaServiceTest {

   @Mock
    private SugerenciaRepository sugerenciaRepository;

    @InjectMocks
    private SugerenciaService sugerenciaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSugerencia() {
        Sugerencia sugerencia = new Sugerencia();
        sugerencia.setDescripcion("Prueba de sugerencia");

        when(sugerenciaRepository.save(sugerencia)).thenReturn(sugerencia);

        Sugerencia saved = sugerenciaService.saveSugerencia(sugerencia);
        assertNotNull(saved);
        assertEquals("Prueba de sugerencia", saved.getDescripcion());
        verify(sugerenciaRepository, times(1)).save(sugerencia);
    }

    @Test
    public void testGetAllSugerencias() {
        Sugerencia s1 = new Sugerencia();
        s1.setId(1L);
        s1.setDescripcion("Sugerencia 1");

        Sugerencia s2 = new Sugerencia();
        s2.setId(2L);
        s2.setDescripcion("Sugerencia 2");

        List<Sugerencia> listaSugerencias = Arrays.asList(s1, s2);
        when(sugerenciaRepository.findAll()).thenReturn(listaSugerencias);

        List<Sugerencia> result = sugerenciaService.getAllSugerencias();
        assertEquals(2, result.size());
        verify(sugerenciaRepository, times(1)).findAll();
    }
}
