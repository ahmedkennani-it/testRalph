package com.sahti.backend.dto;

public class LoginResponse {

    private final String accessToken;
    private final String tokenType;
    private final long expiresInMs;

    public LoginResponse(String accessToken, long expiresInMs) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
        this.expiresInMs = expiresInMs;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresInMs() {
        return expiresInMs;
    }
}
