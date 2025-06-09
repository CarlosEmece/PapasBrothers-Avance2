package com.papasbrother.servicio.impl;


import com.papasbrother.modelo.Usuario;
import com.papasbrother.repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioDetallesServicioImpl implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new User(
            usuario.getEmail(),
            usuario.getPassword(),
            usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toList())
        );
    }
}
