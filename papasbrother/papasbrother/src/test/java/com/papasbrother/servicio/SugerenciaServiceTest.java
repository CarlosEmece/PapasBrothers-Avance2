package com.papasbrother.servicio;

import com.papasbrother.modelo.Sugerencia;
import com.papasbrother.repositorio.SugerenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        when(sugerenciaRepository.save(sugerencia)).thenReturn(sugerencia);
        Sugerencia result = sugerenciaService.saveSugerencia(sugerencia);
        assertEquals(sugerencia, result);
        verify(sugerenciaRepository, times(1)).save(sugerencia);
    }

    @Test
    public void testGetAllSugerencias() {
        Sugerencia s1 = new Sugerencia();
        Sugerencia s2 = new Sugerencia();
        List<Sugerencia> sugerencias = Arrays.asList(s1, s2);
        when(sugerenciaRepository.findAll()).thenReturn(sugerencias);
        List<Sugerencia> result = sugerenciaService.getAllSugerencias();
        assertEquals(2, result.size());
        verify(sugerenciaRepository, times(1)).findAll();
    }

    @Test
    public void testGetSugerenciaById() {
        Sugerencia sugerencia = new Sugerencia();
        when(sugerenciaRepository.findById(1L)).thenReturn(Optional.of(sugerencia));
        Sugerencia result = sugerenciaService.getSugerenciaById(1L);
        assertEquals(sugerencia, result);
        verify(sugerenciaRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateSugerencia() {
        Sugerencia sugerencia = new Sugerencia();
        when(sugerenciaRepository.save(sugerencia)).thenReturn(sugerencia);
        Sugerencia result = sugerenciaService.updateSugerencia(sugerencia);
        assertEquals(sugerencia, result);
        verify(sugerenciaRepository, times(1)).save(sugerencia);
    }

    @Test
    public void testDeleteSugerencia() {
        doNothing().when(sugerenciaRepository).deleteById(1L);
        sugerenciaService.deleteSugerencia(1L);
        verify(sugerenciaRepository, times(1)).deleteById(1L);
    }
}
