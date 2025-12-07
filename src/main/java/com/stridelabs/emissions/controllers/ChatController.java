package com.stridelabs.emissions.controllers;

import com.stridelabs.emissions.dto.ChatRequest;
import com.stridelabs.emissions.dto.ChatResponse;
import com.stridelabs.emissions.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest req) {
    	ChatResponse res = service.respond(req);
    	System.out.println(res.getAnswer());
        return ResponseEntity.ok(res);
    }
}

