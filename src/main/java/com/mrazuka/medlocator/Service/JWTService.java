package com.mrazuka.medlocator.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    @Value("${jwt.secret.key}")
    private String secretKeyString;
    // This will hold the actual SecretKey instance, generated once from the injected string
    private SecretKey SIGNING_KEY;

    // Constructor or @PostConstruct method to initialize the SecretKey from the string
    // @PostConstruct is often preferred for initialization logic after dependency injection
    public JWTService(@Value("${jwt.secret.key}") String secretKeyString) {
        this.secretKeyString = secretKeyString;
        initializeSigningKey();
    }

    // This method ensures the SecretKey is derived only once from the Base64 string.
    private void initializeSigningKey() {
        // Decode the Base64 string to bytes and then create the SecretKey
        // It's crucial that your secretKeyString in application.properties is Base64 encoded
        if (secretKeyString == null || secretKeyString.isEmpty()) {
            throw new IllegalArgumentException("JWT secret key must be configured in application.properties.");
        }
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKeyString);
            SIGNING_KEY = Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            // This catches issues with malformed Base64 strings
            throw new IllegalArgumentException("Failed to decode JWT secret key. Ensure it is a valid Base64 encoded string.", e);
        }
    }

    /**
     * Retrieves the single, consistent signing key used for JWTs.
     * @return The SecretKey for signing and verifying JWTs.
     */
    private SecretKey getSigningKey() {
        // Ensure the key is initialized before use (should be by constructor/PostConstruct)
        if (SIGNING_KEY == null) {
            initializeSigningKey(); // Fallback, though constructor should handle it
        }
        return SIGNING_KEY;
    }
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<String, Object>();

       return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .and()
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token) {
        // extract the email from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
