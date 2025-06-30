package com.papasbrother.controlador;

import com.papasbrother.modelo.Message;
import com.papasbrother.servicio.ChatbotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {
    @Autowired
    private final ChatbotService chatbotService;
    

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/message")
    public Message handleChatbotMessage(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        return chatbotService.processUserMessage(userMessage);
    }
}
