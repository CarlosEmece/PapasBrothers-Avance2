package com.papasbrother.controlador;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;


import com.papasbrother.modelo.Role;
import com.papasbrother.modelo.User;
import com.papasbrother.repositorio.RoleRepository;
import com.papasbrother.repositorio.UserRepository;


@Controller
public class CrearCuentaController {

 private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public CrearCuentaController(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registro")
    public String crearCuenta(@RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
        
        correo = correo.trim().toLowerCase();
        username = username.trim().toLowerCase();
       
        // Imprime valores para depuración
        System.out.println("Registrando usuario: username=[" + username + "], correo=[" + correo + "]");

        // Verifica duplicados
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Ese usuario ya existe, ingrese otro");
            return "crearcuenta";
        }

        if (userRepository.existsByCorreo(correo)) {
            model.addAttribute("error", "Ese correo ya existe, ingrese otro");
            return "crearcuenta";
        }

        // Si no hay duplicados, crea el usuario
        User user = new User();
        user.setNombre(nombre);
        user.setCorreo(correo);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);

        model.addAttribute("nombre", nombre);
        model.addAttribute("correo", correo);
        model.addAttribute("username", username);

        return "Resultados/resultadoCrearCuenta";
    }
}
