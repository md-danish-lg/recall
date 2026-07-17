package com.danish.backend.authflow;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}") private String secretKeyString;
    @Value("${jwt.expiration}") private long jwtExpiration;
    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString));
    }



    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date((System.currentTimeMillis())+ jwtExpiration))
                .signWith(key)
                .compact();
    }


    public Claims getClaims(String token){

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    };

    public String extractUsername(String token){

        Claims claims = getClaims(token);

        return claims.getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails){

        try{
            Claims claims = getClaims(token);
            String username = claims.getSubject();
            boolean isExpired = claims.getExpiration().before(new Date());

            return (username.equals(userDetails.getUsername()) && !isExpired);
        }catch (Exception e){
            return false;
        }

    }

}
