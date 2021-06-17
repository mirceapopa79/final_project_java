package com.sda.javaremote18.spring_boot.config.security;

import com.sda.javaremote18.spring_boot.models.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.String.format;

/**
 * Clasa JwtTokenUtil este clasa raspunzatoare de gestionarea unui token
 * -aici generam token-ul
 * -aici citim date din token
 * -aici validam token-ul(sa nu fie expirat si sa fie valid)
 */
@Component
public class JwtTokenUtil {

    private final String jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private final String jwtIssuer = "com.sda.javaremote18.spring_boot";


    public String generateAccessToken(UserModel user) {
        return Jwts.builder()
                .setSubject(format("%s,%s", user.getId(), user.getEmail()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime() + 30 * 24 * 60 * 60 * 1000)) // 30 de zile valabilitate
                .setExpiration(new Date("31-12-2055")) // 30 de zile valabilitate
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.err.println("Invalid JWT signature - {} " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.err.println("Invalid JWT token - {} " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            System.err.println("Expired JWT token - {} " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            System.err.println("Unsupported JWT token - {} " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.err.println("JWT claims string is empty - {} " + ex.getMessage());
        }
        return false;
    }

}