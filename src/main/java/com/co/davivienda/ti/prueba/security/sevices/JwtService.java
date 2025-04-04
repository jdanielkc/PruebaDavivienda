package com.co.davivienda.ti.prueba.security.sevices;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * JwtService is a service that provides methods for generating and validating
 * JWT tokens.
 * It uses the secret key defined in the application properties to sign the
 * tokens.
 * This class is annotated with @Service to indicate that it is a Spring service
 * component.
 * 
 * @author Jose Daniel Garcia Arias
 * @version 1.0.0
 * @since 2025/04/03
 */
@Service
public class JwtService {

    @Value("${jwt.secret:defaultsecretkey}")
    private String secretKey;

    private static final long JWT_TOKEN_VALIDITY = 86400000; // 24 horas

    /**
     * Generates a JWT token for the given email.
     * 
     * @param email the email for which the token is generated
     * @return the generated JWT token
     */
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    /**
     * Validates the given JWT token.
     * 
     * @param token the JWT token to be validated
     * @param email the email associated with the token
     * @return true if the token is valid, false otherwise
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the given JWT token and checks if it is expired.
     * 
     * @param token the JWT token to be validated
     * @param email the email associated with the token
     * @return true if the token is valid and not expired, false otherwise
     */
    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Checks if the given JWT token is expired.
     * 
     * @param token the JWT token to be checked
     * @return true if the token is expired, false otherwise
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Checks if the given JWT token is expired.
     * 
     * @param token the JWT token to be checked
     * @return true if the token is expired, false otherwise
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Checks if the given JWT token is expired.
     * 
     * @param token the JWT token to be checked
     * @return true if the token is expired, false otherwise
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}