package com.BackBasiFit.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final Key key;
    private final long expirationMs;

    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expirationMinutes}") long expirationMinutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMinutes * 60_000;
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(now).setExpiration(exp).signWith(key).compact();
    }

    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date exp = extractAllClaims(token).getExpiration();

        return exp.before(new Date());
    }

    private Claims extractAllClaims(String token) {
        
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
