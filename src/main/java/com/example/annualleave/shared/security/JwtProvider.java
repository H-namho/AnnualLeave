package com.example.annualleave.shared.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;
    private SecretKey secretKey;

    @PostConstruct
    public void init(){
        this.secretKey= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long userId,String name, String role,boolean isAccess){

        Date now = new Date();
        long expiry = isAccess ? accessTokenExpiration : refreshTokenExpiration;
        Date expiryDate = new Date(now.getTime()+expiry);
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("name",name)
                .claim("role", role)
                .signWith(secretKey)
                .issuedAt(now)
                .expiration(expiryDate)
                .compact();
    }

    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build().parseSignedClaims(token).getPayload();
    }

    public long getRefreshTokenExpiration(){
        return refreshTokenExpiration/1000;
    }

    public long getRemainExpiration(String token){

        long expirationTime = getClaims(token).getExpiration().getTime();
        long currentTime = System.currentTimeMillis();
        return Math.max(expirationTime-currentTime,0);

    }

}
