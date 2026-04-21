package org.whatsapp.springboot.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.whatsapp.springboot.dtos.OtpRecord;
import org.whatsapp.springboot.repositories.OtpRepository;

import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class OtpService {

    private final OtpRepository repository;
    private final WhatsAppService whatsAppService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final SecureRandom random = new SecureRandom();

    public OtpService(OtpRepository repository, WhatsAppService whatsAppService) {
        this.repository = repository;
        this.whatsAppService = whatsAppService;
    }

    public Mono<Map<String, Object>> requestOtp(String phone) {
        String code = generateSixDigitCode();
        String hashed = encoder.encode(code);
        Instant expiresAt = Instant.now().plus(5, ChronoUnit.MINUTES);

        repository.save(phone, new OtpRecord(phone, hashed, expiresAt));

        System.out.println("OTP for " + phone + " = " + code);

        return whatsAppService.sendHelloWorld(phone)
                .map(resp -> Map.of(
                        "success", true,
                        "message", "OTP generated. WhatsApp transport smoke test sent.",
                        "expiresAt", expiresAt.toString(),
                        "whatsappMessageId", resp.getMessages() != null && !resp.getMessages().isEmpty()
                                ? resp.getMessages().get(0).getId()
                                : "N/A"
                ));
    }

    public Map<String, Object> verifyOtp(String phone, String code) {
        OtpRecord record = repository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("No OTP request found for this phone"));

        if (record.isUsed()) {
            throw new RuntimeException("OTP already used");
        }

        if (Instant.now().isAfter(record.getExpiresAt())) {
            repository.delete(phone);
            throw new RuntimeException("OTP expired");
        }

        if (record.getAttempts() >= 5) {
            repository.delete(phone);
            throw new RuntimeException("Too many failed attempts");
        }

        if (!encoder.matches(code, record.getHashedCode())) {
            record.incrementAttempts();
            throw new RuntimeException("Invalid OTP");
        }

        record.markUsed();
        return Map.of(
                "success", true,
                "message", "OTP verified successfully"
        );
    }

    private String generateSixDigitCode() {
        int value = 100000 + random.nextInt(900000);
        return String.valueOf(value);
    }
}