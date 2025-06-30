package com.papasbrother.servicio;

import com.papasbrother.modelo.ChatResponse;
import com.papasbrother.modelo.Message;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatbotService {

    private List<ChatResponse> responseConfigurations;
    private String lastContext = null;

    @PostConstruct
    public void init() {
        responseConfigurations = new ArrayList<>();

        // Saludos
        responseConfigurations.add(new ChatResponse(
            "greetings",
            List.of("hola", "hi", "saludos", "buenos días", "buenas tardes", "buenas noches"),
            "¡Hola! 👋 Soy el asistente de Papas Brother. ¿En qué puedo ayudarte hoy?",
            "text"
        ));

        // Menú general
        responseConfigurations.add(new ChatResponse(
            "menu",
            List.of("menú", "menu", "carta", "productos"),
            "🍔 <strong>Menú Principal:</strong><br>- Hamburguesas<br>- Pollos Broaster<br>- Salchipapas<br>- Alitas BBQ<br><br>¿Te interesa alguno?",
            "menu"
        ));

        // Categorías específicas
        responseConfigurations.add(new ChatResponse(
            "hamburguesas",
            List.of("hamburguesa", "hamburguesas"),
            "🥩 <strong>Hamburguesas:</strong><br>- Clásica<br>- Doble carne<br>- BBQ Bacon<br>- Papas Combo",
            "text"
        ));

        responseConfigurations.add(new ChatResponse(
            "pollos",
            List.of("pollo", "broaster", "pollos"),
            "🍗 <strong>Pollos Broaster:</strong><br>- 1/4 Broaster<br>- 1/2 Pollo<br>- Combo Familiar",
            "text"
        ));

        responseConfigurations.add(new ChatResponse(
            "salchipapas",
            List.of("salchipapas", "salchi", "papas con salchicha"),
            "🍟 <strong>Salchipapas:</strong><br>- Clásica<br>- Especial<br>- Suprema",
            "text"
        ));

        responseConfigurations.add(new ChatResponse(
            "alitas",
            List.of("alitas", "bbq", "alitas bbq"),
            "🔥 <strong>Alitas BBQ:</strong><br>- 6 unidades<br>- 12 unidades<br>- Combo con papas",
            "text"
        ));

        // Promociones
        responseConfigurations.add(new ChatResponse(
            "promociones",
            List.of("promo", "promociones", "descuento", "ofertas"),
            "🎉 <strong>Promociones:</strong><br>- 2x1 en Hamburguesas (Lunes)<br>- 20% de descuento en Pollos Broaster (Miércoles)<br>- Combo Familiar a S/25 (Fines de semana)",
            "text"
        ));

        // Horarios
        responseConfigurations.add(new ChatResponse(
            "horarios",
            List.of("horario", "hora", "horarios", "abren", "cierran"),
            "🕒 <strong>Horario de atención:</strong><br>Lunes a Domingo de 12:00 p.m. a 11:00 p.m.",
            "text"
        ));

        // Dirección
        responseConfigurations.add(new ChatResponse(
            "direccion",
            List.of("dirección", "dónde están", "ubicación", "local"),
            "📍 <strong>Estamos ubicados en:</strong><br>Av. Los Sabores 123, Lima, Perú",
            "text"
        ));

        // Teléfono
        responseConfigurations.add(new ChatResponse(
            "telefono",
            List.of("teléfono", "celular", "llamar", "contacto"),
            "📞 <strong>Número de contacto:</strong><br>+51 987 654 321",
            "text"
        ));
    }

    public Message processUserMessage(String userMessage) {
        String lowerMessage = userMessage.toLowerCase().trim();

        // 1. Respuestas de seguimiento como “sí”, “quiero”, etc.
        if (lastContext != null && List.of("sí", "quiero", "ver", "muéstrame", "dime", "ok", "dale").stream()
                .anyMatch(lowerMessage::equals)) {
            return responderPorContexto();
        }

        // 2. Buscar respuesta por palabra clave
        Optional<ChatResponse> bestResponse = responseConfigurations.stream()
            .filter(resp -> resp.getKeywords().stream().anyMatch(k -> lowerMessage.contains(k)))
            .findFirst();

        if (bestResponse.isPresent()) {
            lastContext = bestResponse.get().getResponseType();
            return new Message(bestResponse.get().getResponse(), "bot", true);
        }

        // 3. No se encontró coincidencia
        return new Message(getDefaultResponse(), "bot", true);
    }

    private Message responderPorContexto() {
        switch (lastContext) {
            case "menu":
                return new Message("¿Te gustaría ver más detalles sobre <strong>hamburguesas</strong>, <strong>pollos</strong>, <strong>salchipapas</strong> o <strong>alitas</strong>?", "bot", true);
            case "text":
                return new Message("¿Podrías especificar mejor sobre qué tema deseas información? Puedes preguntar por <strong>promociones</strong>, <strong>dirección</strong> o <strong>horarios</strong>.", "bot", true);
            default:
                return new Message(getDefaultResponse(), "bot", true);
        }
    }

    private String getDefaultResponse() {
        List<String> defaultOptions = List.of(
            "Puedes preguntarme sobre nuestro <strong>menú</strong>, <strong>promociones</strong> o <strong>horarios</strong>.",
            "¿Necesitas saber nuestra <strong>dirección</strong> o <strong>teléfono</strong>?",
            "Consultame sobre <strong>hamburguesas</strong>, <strong>pollos</strong>, <strong>alitas</strong> o <strong>salchipapas</strong>."
        );
        return "🤔 No estoy seguro de lo que preguntas. " +
               defaultOptions.get((int) (Math.random() * defaultOptions.size()));
    }
}