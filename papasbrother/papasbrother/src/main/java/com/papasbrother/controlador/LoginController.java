package com.papasbrother.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    
    @PostMapping("/login")
    public ModelAndView login(
            @RequestParam String username1,
            @RequestParam String password1) {
                
        
        ModelAndView mav = new ModelAndView("Resultados/resultadoLogin");
        mav.addObject("username1", username1);
        return mav;
    }
}