package com.health.fitness.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
public class User implements UserDetails {  // 이 부분 추가

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  private String nickname;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int point;

  @Column(columnDefinition = "INT DEFAULT 1")
  private int level;

  @Column(columnDefinition = "VARCHAR(10) DEFAULT 'USER'")
  private String role;

  // ↓ UserDetails 필수 구현 메서드들
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role));
  }

  @Override
  public String getUsername() {
    return email;  // Spring Security에서 username = email
  }
}