package org.whatsapp.springboot.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.whatsapp.springboot.dtos.WhatsAppSendResponse;
import org.whatsapp.springboot.services.WhatsAppService;

import jakarta.validation.constraints.NotBlank;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/whatsapp")
@Validated
public class WhatsAppController {

    private final WhatsAppService whatsAppService;

    public WhatsAppController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    @PostMapping("/hello")
    public Mono<WhatsAppSendResponse> sendHello(@RequestParam @NotBlank String to) {
        return whatsAppService.sendHelloWorld(to);
    }

    @PostMapping("/broadcast")
    public ResponseEntity<?> broadcast(
        @RequestParam List<String> toPhones,
        @RequestParam @NotBlank String message
    ) {
        return ResponseEntity.ok(whatsAppService.broadCastMessage(toPhones, message));
    }
    
}