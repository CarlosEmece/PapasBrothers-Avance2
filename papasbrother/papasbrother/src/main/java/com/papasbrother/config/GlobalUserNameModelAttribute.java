package com.papasbrother.config;

import com.papasbrother.repositorio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@Component
@ControllerAdvice
public class GlobalUserNameModelAttribute {
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addUserNameAttribute(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean autenticado = auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal());
        String nombreUsuario = "";
        if (autenticado) {
            String username = auth.getName();
            nombreUsuario = userRepository.findByUsername(username).map(u -> u.getNombre()).orElse("");
        }
        model.addAttribute("nombreUsuario", nombreUsuario);
    }
}
