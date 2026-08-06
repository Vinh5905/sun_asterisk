package com.sunasterisk.employeemanagement.service;

import java.text.Normalizer;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {

    private final PasswordEncoder passwordEncoder;

    public UtilityService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String normalizeText(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        String trimmed = value.trim().replaceAll("\\s+", " ");
        return Normalizer.normalize(trimmed, Normalizer.Form.NFC);
    }

    public String formatName(String name) {
        String normalizedName = normalizeText(name).toLowerCase(Locale.ROOT);
        if (normalizedName.isEmpty()) {
            return "";
        }

        String[] words = normalizedName.split(" ");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            if (!formattedName.isEmpty()) {
                formattedName.append(" ");
            }
            formattedName
                .append(Character.toUpperCase(word.charAt(0)))
                .append(word.substring(1));
        }

        return formattedName.toString();
    }

    public String generateEmployeeCode(long employeeNumber) {
        return String.format("EMP%05d", employeeNumber);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
