package com.stridelabs.emissions.dto;

public class ChatRequest {
    private String message;

    // --- Constructors ---
    public ChatRequest() {
    }

    public ChatRequest(String message) {
        this.message = message;
    }

    // --- Getter & Setter ---
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
