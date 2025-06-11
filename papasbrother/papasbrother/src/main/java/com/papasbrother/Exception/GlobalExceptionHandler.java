package com.papasbrother.Exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejar excepciones específicas de recursos no encontrados
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFound(ResourceNotFoundException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }

    // Manejar cualquier otra excepción
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception ex, Model model) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", "Ocurrió un error: " + ex.getMessage());
        return mav;
    }
}
