package com.papasbrother.modelo;

import lombok.Data;
import java.util.List;

@Data
public class ChatResponse {
    private String responseId;               // ID interno para identificar la respuesta
    private List<String> keywords;           // Palabras clave asociadas a esta respuesta
    private String response;                 // Texto que responderá el bot
    private String responseType;             // Tipo de respuesta: "text", "menu", etc.
    private boolean needsFollowUp;           // Indica si se espera una respuesta adicional

    // Constructor
    public ChatResponse(String responseId, List<String> keywords, String response, String responseType) {
        this.responseId = responseId;
        this.keywords = keywords;
        this.response = response;
        this.responseType = responseType;
        this.needsFollowUp = responseType.equalsIgnoreCase("menu") || responseType.equalsIgnoreCase("options");
    }
}

