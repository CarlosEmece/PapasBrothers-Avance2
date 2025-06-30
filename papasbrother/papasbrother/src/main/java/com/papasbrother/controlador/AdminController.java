package com.papasbrother.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;

import com.papasbrother.repositorio.UserRepository;
import com.papasbrother.servicio.ContactoService;
import com.papasbrother.servicio.EmailService;
import com.papasbrother.servicio.PromocionService;
import com.papasbrother.servicio.SugerenciaService;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SugerenciaService sugerenciaService;

    @Autowired
    private PromocionService promocionService;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailService emailService;

    // Página de login del administrador
    @GetMapping("/login")
    public String adminLogin() {
        return "admin/login"; // Vista: src/main/resources/templates/admin/login.html
    }

    // Panel principal del administrador
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/administradorpanel")
    public String adminPanel(Model model) {
        model.addAttribute("sugerencias", sugerenciaService.getAllSugerencias());
        model.addAttribute("promociones", promocionService.getAllPromociones());
        model.addAttribute("contactos", contactoService.getAllContactos());
        model.addAttribute("usuarios", userRepository.findAll());
        
        return "administradorpanel"; // Vista: src/main/resources/templates/admin/gestionar.html
    }

    // Enviar mensaje a usuarios seleccionados
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enviar-mensaje")
    public String enviarMensajeUsuarios(@RequestParam("correos") List<String> correos,
                                        @RequestParam("tipoMensaje") String tipoMensaje,
                                        Model model) {
        String asunto = "";
        String mensaje = "";
        switch (tipoMensaje) {
            case "PROMOCIONES":
                asunto = "¡Nuevas Promociones en Papas Brother!";
                mensaje = "¡Hola!\n\nTenemos nuevas promociones exclusivas para ti en Papas Brother.\n\n" +
                          "- 2x1 en papas medianas todos los martes.\n" +
                          "- 20% de descuento en combos familiares este fin de semana.\n" +
                          "- Delivery gratis en pedidos mayores a S/ 50.\n\n" +
                          "¡No te pierdas estas ofertas y síguenos en nuestras redes sociales para más sorpresas!\n\n" +
                          "Saludos,\nEl equipo de Papas Brother";
                break;
            case "MENU":
                asunto = "Nuestro Menú Actualizado";
                mensaje = "¡Hola!\n\nTe invitamos a descubrir nuestro menú actualizado con nuevas opciones deliciosas:\n\n" +
                          "- Papas clásicas, papas con queso, papas con toppings.\n" +
                          "- Hamburguesas, alitas, salchipapas y más.\n" +
                          "- Bebidas y postres para acompañar tu pedido.\n\n" +
                          "Consulta nuestro menú completo en nuestra web o app.\n\n" +
                          "¡Esperamos tu pedido!\nEl equipo de Papas Brother";
                break;
            case "OFERTAS":
                asunto = "Ofertas Especiales Papas Brother";
                mensaje = "¡Hola!\n\nAprovecha nuestras ofertas especiales por tiempo limitado:\n\n" +
                          "- 10% de descuento en tu primer pedido online.\n" +
                          "- Combo Papas + Bebida a solo S/ 15.\n" +
                          "- Participa en nuestro sorteo mensual por un combo gratis.\n\n" +
                          "Haz tu pedido ahora y disfruta de Papas Brother.\n\n" +
                          "¡Gracias por preferirnos!\nEl equipo de Papas Brother";
                break;
            default:
                asunto = "Mensaje de Papas Brother";
                mensaje = "Gracias por ser parte de nuestra comunidad.";
        }
        emailService.enviarCorreoMultiple(correos, asunto, mensaje);
        model.addAttribute("mensajeExito", "¡Mensaje enviado correctamente a los usuarios seleccionados!");
        // Recargar datos para el panel
        model.addAttribute("sugerencias", sugerenciaService.getAllSugerencias());
        model.addAttribute("promociones", promocionService.getAllPromociones());
        model.addAttribute("contactos", contactoService.getAllContactos());
        model.addAttribute("usuarios", userRepository.findAll());
        return "administradorpanel";
    }
}