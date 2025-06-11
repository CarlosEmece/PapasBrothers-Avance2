package com.papasbrother;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import com.papasbrother.modelo.Contacto;
import com.papasbrother.repositorio.ContactoRepository;
import com.papasbrother.servicio.ContactoService;
import java.util.List;
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
        contacto.setNombreApellido("Maria Garcia");
        contacto.setCorreo("maria@example.com");
        // Establece otros datos del contacto según corresponda

        when(contactoRepository.save(contacto)).thenReturn(contacto);

        Contacto saved = contactoService.saveContacto(contacto);
        assertNotNull(saved);
        assertEquals("Maria Garcia", saved.getNombreApellido());
        verify(contactoRepository, times(1)).save(contacto);
    }

    @Test
    public void testGetAllContactos() {
        Contacto c1 = new Contacto();
        c1.setId(1L);
        c1.setNombreApellido("Maria Garcia");

        Contacto c2 = new Contacto();
        c2.setId(2L);
        c2.setNombreApellido("Carlos Lopez");

        List<Contacto> listaContactos = Arrays.asList(c1, c2);
        when(contactoRepository.findAll()).thenReturn(listaContactos);

        List<Contacto> result = contactoService.getAllContactos();
        assertEquals(2, result.size());
        verify(contactoRepository, times(1)).findAll();
    }
}
