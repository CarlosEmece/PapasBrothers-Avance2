    package com.papasbrother.controlador;

    import com.papasbrother.modelo.*;
    import com.papasbrother.repositorio.*;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    @Controller
    @RequiredArgsConstructor
    public class AutenticacionControlador {

        private final UsuarioRepositorio usuarioRepositorio;
        private final RolRepositorio rolRepositorio;
        private final PasswordEncoder passwordEncoder;

        @GetMapping("/login")
        public String mostrarLogin() {
            return "login";
        }

        @GetMapping("/registro")
        public String mostrarRegistro(Model modelo) {
            modelo.addAttribute("usuario", new Usuario());
            return "registro";
        }

        @PostMapping("/registro")
        public String registrarUsuario(@ModelAttribute Usuario usuario) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            Rol rolUsuario = rolRepositorio.findByNombre("ROLE_USER");
            if (rolUsuario == null) {
                rolUsuario = new Rol();
                rolUsuario.setNombre("ROLE_USER");
                rolRepositorio.save(rolUsuario);
            }

            usuario.getRoles().add(rolUsuario);
            usuarioRepositorio.save(usuario);
            return "redirect:/login";
        }
    }
