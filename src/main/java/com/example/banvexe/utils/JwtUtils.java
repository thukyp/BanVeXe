package com.example.banvexe.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {
    private String jwtSecret = "ChuoiBiMatSieuCapVipProDungChoDuAnBanVeXe123456"; // Phải dài ít nhất 32 ký tự
    private int jwtExpirationMs = 86400000; // 1 ngày

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
}