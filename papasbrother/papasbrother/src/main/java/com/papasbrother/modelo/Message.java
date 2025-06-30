package com.papasbrother.modelo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Message {
    private String content;
    private String sender; // "user" o "bot"
    private LocalDateTime timestamp;
    private boolean isHtml;

    public Message(String content, String sender, boolean isHtml) {
        this.content = content;
        this.sender = sender;
        this.isHtml = isHtml;
        this.timestamp = LocalDateTime.now();
    }
}

