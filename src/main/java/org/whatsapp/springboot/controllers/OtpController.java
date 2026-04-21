package org.whatsapp.springboot.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.whatsapp.springboot.dtos.RequestOtpRequest;
import org.whatsapp.springboot.dtos.VerifyOtpRequest;
import org.whatsapp.springboot.services.OtpService;

import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/request")
    public Mono<Map<String, Object>> requestOtp(@Valid @RequestBody RequestOtpRequest request) {
        return otpService.requestOtp(request.getPhone());
    }

    @PostMapping("/verify")
    public Map<String, Object> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        return otpService.verifyOtp(request.getPhone(), request.getCode());
    }
}