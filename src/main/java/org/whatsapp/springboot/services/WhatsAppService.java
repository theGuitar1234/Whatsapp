package org.whatsapp.springboot.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.whatsapp.springboot.dtos.WhatsAppSendResponse;
import org.whatsapp.springboot.utilities.configurations.WhatsAppProperties;

import reactor.core.publisher.Mono;

@Service
public class WhatsAppService {

        private final WebClient webClient;
        private final WhatsAppProperties properties;

        public WhatsAppService(WebClient.Builder builder, WhatsAppProperties properties) {
                this.properties = properties;
                this.webClient = builder
                                .baseUrl(properties.getBaseUrl())
                                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + properties.getToken())
                                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .build();
        }

        public List<Object> broadCastMessage(List<String> toPhones, String message) {
                List<Object> results = new ArrayList();
                
                for (String phone : toPhones) {
                        try {
                            WhatsAppSendResponse response = sendIndividual(phone, message).block();
                            results.add(Map.of(
                                        "phone", phone,
                                        "success", true,
                                        "response", response
                                ));
                                System.out.println("Send Message to: " + phone);
                                System.out.println("Moving on to the next...");
                        } catch (Exception e) {
                                results.add(Map.of(
                                        "phone", phone,
                                        "success", false,
                                        "error", e.getMessage()
                                ));
                                System.err.println("Something went Wrong for : " + phone + " " + e.getMessage());
                        }
                }

                return results;
        }

        public Mono<WhatsAppSendResponse> sendIndividual(String toPhone, String message) {
                Map<String, Object> payload = Map.of(
                                "messaging_product", "whatsapp",
                                "to", toPhone,
                                "type", "template",
                                "template", Map.of(
                                                "name", message,
                                                "language", Map.of("code", properties.getLanguageCode())));
                return webClient.post()
                                .uri("/{version}/{phoneNumberId}/messages",
                                                properties.getApiVersion(),
                                                properties.getPhoneNumberId())
                                .bodyValue(payload)
                                .retrieve()
                                .bodyToMono(WhatsAppSendResponse.class)
                                .doOnEach((e) -> System.err.println(e));
        }

        public Mono<WhatsAppSendResponse> sendHelloWorld(String toPhone) {
                Map<String, Object> payload = Map.of(
                                "messaging_product", "whatsapp",
                                "to", toPhone,
                                "type", "template",
                                "template", Map.of(
                                                "name", properties.getHelloWorldTemplate(),
                                                "language", Map.of("code", properties.getLanguageCode())));

                return webClient.post()
                                .uri("/{version}/{phoneNumberId}/messages",
                                                properties.getApiVersion(),
                                                properties.getPhoneNumberId())
                                .bodyValue(payload)
                                .retrieve()
                                .bodyToMono(WhatsAppSendResponse.class)
                                .doOnEach((e) -> System.err.println(e));
        }

        public Mono<WhatsAppSendResponse> sendTemplate(String toPhone,
                        String templateName,
                        String languageCode,
                        Object components) {

                Map<String, Object> template = new java.util.HashMap<>();
                template.put("name", templateName);
                template.put("language", Map.of("code", languageCode));

                if (components != null) {
                        template.put("components", components);
                }

                Map<String, Object> payload = Map.of(
                                "messaging_product", "whatsapp",
                                "to", toPhone,
                                "type", "template",
                                "template", template);

                return webClient.post()
                                .uri("/{version}/{phoneNumberId}/messages",
                                                properties.getApiVersion(),
                                                properties.getPhoneNumberId())
                                .bodyValue(payload)
                                .retrieve()
                                .bodyToMono(WhatsAppSendResponse.class);
        }
}