package com.isanjalee.demo.springbootdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    private String secret;
    private long expiryMs;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiryMs() {
        return expiryMs;
    }

    public void setExpiryMs(long expiryMs) {
        this.expiryMs = expiryMs;
    }
}
