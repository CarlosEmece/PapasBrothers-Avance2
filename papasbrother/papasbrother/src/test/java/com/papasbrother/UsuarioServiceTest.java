package com.papasbrother;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.papasbrother.modelo.Usuario;
import com.papasbrother.repositorio.UsuarioRepository;
import com.papasbrother.servicio.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setup() {
        // Abre los mocks para que se inyecten en usuarioService
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombreCompleto("Juan Perez");
        usuario.setEmail("juan@example.com");
        usuario.setPassword("secret");

        // Simula que al guardar, se retorna el objeto igual
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario saved = usuarioService.saveUsuario(usuario);
        assertNotNull(saved);
        assertEquals("Juan Perez", saved.getNombreCompleto());

        // Verifica que se llamó una vez a save con usuario
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testFindByEmail() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombreCompleto("Juan Perez");
        usuario.setEmail("juan@example.com");

        // Simula que al buscar por email se retorna el usuario
        when(usuarioRepository.findByEmail("juan@example.com"))
           .thenReturn(java.util.Optional.of(usuario));

        Usuario found = usuarioService.findByEmail("juan@example.com");
        assertNotNull(found);
        assertEquals(1L, found.getId());

        verify(usuarioRepository, times(1)).findByEmail("juan@example.com");
    }
}
