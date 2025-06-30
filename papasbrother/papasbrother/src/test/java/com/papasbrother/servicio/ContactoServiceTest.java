package com.papasbrother.servicio;

import com.papasbrother.modelo.Contacto;
import com.papasbrother.repositorio.ContactoRepository;
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

public class ContactoServiceTest {
    @Mock
    private ContactoRepository contactoRepository;

    @InjectMocks
    private ContactoService contactoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveContacto() {
        Contacto contacto = new Contacto();
        when(contactoRepository.save(contacto)).thenReturn(contacto);
        Contacto result = contactoService.saveContacto(contacto);
        assertEquals(contacto, result);
        verify(contactoRepository, times(1)).save(contacto);
    }

    @Test
    public void testGetAllContactos() {
        Contacto c1 = new Contacto();
        Contacto c2 = new Contacto();
        List<Contacto> contactos = Arrays.asList(c1, c2);
        when(contactoRepository.findAll()).thenReturn(contactos);
        List<Contacto> result = contactoService.getAllContactos();
        assertEquals(2, result.size());
        verify(contactoRepository, times(1)).findAll();
    }

    @Test
    public void testGetContactoById() {
        Contacto contacto = new Contacto();
        when(contactoRepository.findById(1L)).thenReturn(Optional.of(contacto));
        Contacto result = contactoService.getContactoById(1L);
        assertEquals(contacto, result);
        verify(contactoRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteContacto() {
        doNothing().when(contactoRepository).deleteById(1L);
        contactoService.deleteContacto(1L);
        verify(contactoRepository, times(1)).deleteById(1L);
    }
}
