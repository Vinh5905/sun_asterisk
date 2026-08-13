package com.sunasterisk.employeemanagement.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunasterisk.employeemanagement.model.AppUser;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String HMAC_SHA256 = "HmacSHA256";

    private final ObjectMapper objectMapper;
    private final String secret;
    private final long expirationSeconds;

    public JwtService(
        ObjectMapper objectMapper,
        @Value("${app.jwt.secret}") String secret,
        @Value("${app.jwt.expiration-seconds}") long expirationSeconds
    ) {
        this.objectMapper = objectMapper;
        this.secret = secret;
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(AppUser user) {
        try {
            Map<String, Object> header = Map.of(
                "alg", "HS256",
                "typ", "JWT"
            );
            Map<String, Object> payload = Map.of(
                "sub", user.getUsername(),
                "role", user.getRole().name(),
                "exp", Instant.now().plusSeconds(expirationSeconds).getEpochSecond()
            );

            String encodedHeader = encodeJson(header);
            String encodedPayload = encodeJson(payload);
            String unsignedToken = encodedHeader + "." + encodedPayload;

            return unsignedToken + "." + sign(unsignedToken);
        } catch (Exception exception) {
            throw new IllegalStateException("Could not generate JWT token", exception);
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Map<String, Object> claims = extractClaims(token);
            Number expiration = (Number) claims.get("exp");
            return expiration.longValue() > Instant.now().getEpochSecond();
        } catch (Exception exception) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return (String) extractClaims(token).get("sub");
    }

    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    private Map<String, Object> extractClaims(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT token");
            }

            String unsignedToken = parts[0] + "." + parts[1];
            if (!sign(unsignedToken).equals(parts[2])) {
                throw new IllegalArgumentException("Invalid JWT signature");
            }

            byte[] decodedPayload = Base64.getUrlDecoder().decode(parts[1]);
            return objectMapper.readValue(decodedPayload, new TypeReference<>() {
            });
        } catch (Exception exception) {
            throw new IllegalArgumentException("Invalid JWT token", exception);
        }
    }

    private String encodeJson(Map<String, Object> value) throws Exception {
        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(objectMapper.writeValueAsBytes(value));
    }

    private String sign(String value) throws Exception {
        Mac mac = Mac.getInstance(HMAC_SHA256);
        SecretKeySpec key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
        mac.init(key);
        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
    }
}
