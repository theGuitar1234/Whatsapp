package org.whatsapp.springboot.dtos;

import java.time.Instant;

public class OtpRecord {
    private final String phone;
    private final String hashedCode;
    private final Instant expiresAt;
    private int attempts;
    private boolean used;

    public OtpRecord(String phone, String hashedCode, Instant expiresAt) {
        this.phone = phone;
        this.hashedCode = hashedCode;
        this.expiresAt = expiresAt;
        this.attempts = 0;
        this.used = false;
    }

    public String getPhone() { return phone; }
    public String getHashedCode() { return hashedCode; }
    public Instant getExpiresAt() { return expiresAt; }
    public int getAttempts() { return attempts; }
    public boolean isUsed() { return used; }

    public void incrementAttempts() { this.attempts++; }
    public void markUsed() { this.used = true; }
}