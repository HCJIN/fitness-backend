package com.health.fitness.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  private SecretKey key;

  private final long EXPIRATION = 1000 * 60 * 60 * 24; // 24시간

  // 🔥 앱 시작 시 한 번만 Key 생성 (성능 + 안정성)
  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
  }

  // 토큰 생성
  public String generateToken(String email) {
    return Jwts.builder()
        .subject(email)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
        .signWith(key)
        .compact();
  }

  // 토큰에서 이메일 추출
  public String getEmail(String token) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  // 토큰 유효성 검사
  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(key)
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
}
