package org.whatsapp.springboot.repositories;

import org.springframework.stereotype.Repository;
import org.whatsapp.springboot.dtos.OtpRecord;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OtpRepository {
    private final Map<String, OtpRecord> store = new ConcurrentHashMap<>();

    public void save(String phone, OtpRecord record) {
        store.put(phone, record);
    }

    public Optional<OtpRecord> findByPhone(String phone) {
        return Optional.ofNullable(store.get(phone));
    }

    public void delete(String phone) {
        store.remove(phone);
    }
}