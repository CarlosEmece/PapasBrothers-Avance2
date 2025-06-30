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
public class GlobalModelAttributes {
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean autenticado = auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal());
        String correoUsuario = "";
        if (autenticado) {
            String username = auth.getName();
            correoUsuario = userRepository.findByUsername(username).map(u -> u.getCorreo()).orElse("");
        }
        model.addAttribute("autenticado", autenticado);
        model.addAttribute("correoUsuario", correoUsuario);
    }
}
