package com.sahti.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService("test-secret-for-unit-tests-only", 3600000);

    @Test
    void generatesTokenThatCanBeParsedBackToSameSubjectAndUserId() {
        String token = jwtService.generateToken(42L, "user@example.com");

        Claims claims = jwtService.parseClaims(token);

        assertThat(claims.getSubject()).isEqualTo("user@example.com");
        assertThat(claims.get("userId", Integer.class)).isEqualTo(42);
    }

    @Test
    void rejectsTamperedToken() {
        String token = jwtService.generateToken(1L, "user@example.com");
        String tampered = token.substring(0, token.length() - 1) + (token.endsWith("a") ? "b" : "a");

        assertThatThrownBy(() -> jwtService.parseClaims(tampered)).isInstanceOf(JwtException.class);
    }

    @Test
    void rejectsTokenSignedWithDifferentSecret() {
        JwtService otherService = new JwtService("a-completely-different-secret", 3600000);
        String token = otherService.generateToken(1L, "user@example.com");

        assertThatThrownBy(() -> jwtService.parseClaims(token)).isInstanceOf(JwtException.class);
    }
}
