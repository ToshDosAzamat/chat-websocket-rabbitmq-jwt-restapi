package com.example.chatwebsocketkafkajwtrestapi.component;

import com.example.chatwebsocketkafkajwtrestapi.exception.NotFoundException;
import com.example.chatwebsocketkafkajwtrestapi.model.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class JwtTokenProvider {
    private PasswordEncoder passwordEncoder;

    public JwtTokenProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    // generate JWT token
    public String generateToken(User user){
        Map<String ,Object> claims = new HashMap<>();
        claims.put("first_name", user.getFirstName());
        claims.put("last_name", user.getLastName());
        claims.put("username", user.getUsername());
        claims.put("status", user.getStatus());
        claims.put("role", user.getRoles());
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        try{
            return Jwts.builder()
                    .setClaims(claims)
                    .subject(user.getUsername())
                    .issuedAt(currentDate)
                    .expiration(expireDate)
                    .signWith(key())
                    .compact();
        }catch (JwtException exception) {
            throw new NotFoundException("Failed to generate token: " + exception.getMessage());
        }
    }
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    // get username from JWT token
    public String getUsername(String token){
        try{
            return Jwts.parser()
                    .verifyWith( key())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception exception){
            throw new NotFoundException("Failed to get username token: " + exception.getMessage());
        }

    }
      public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}