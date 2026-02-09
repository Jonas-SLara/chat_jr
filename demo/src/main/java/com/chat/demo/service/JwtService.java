package com.chat.demo.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long expirationTime;

    //criar um token sem as clains extras
    public String generateTokenDefault(UserDetails user){
        return generateToken(Map.of(), user);
    }

    //criar um token contendo todas as clains
    public String generateToken(Map<String, Object> clains, UserDetails user){
        return Jwts.builder()
            .setClaims(clains)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    //decodifica a chave
    private Key getSignInKey(){
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    //extrai todas as clains do token
    private Claims extractClaims(String token){
        return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

    public String getUserNameFromToken(String token){
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    public Date getExpirationFromToken(String token){
        Claims claims = extractClaims(token);
        return claims.getExpiration();
    }

    public boolean isTokenExpired(String token){
        return getExpirationFromToken(token).before(new Date());
    }

    public boolean isValidToken(String token, UserDetails user){
        try {
            String username = getUserNameFromToken(token);
            return username.equals(user.getUsername()) && !isTokenExpired(token);  
        } catch (Exception e) {
            return false;
        }
    }
}
