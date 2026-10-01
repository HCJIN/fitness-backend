package com.health.fitness.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(unique = true)
  private String nickname;

  @Builder.Default
  @Column(columnDefinition = "INT DEFAULT 0")
  private int point = 0;

  @Builder.Default
  @Column(columnDefinition = "INT DEFAULT 1")
  private int level = 1;

  @Builder.Default
  @Column(columnDefinition = "VARCHAR(10) DEFAULT 'USER'")
  private String role = "USER";

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDate createdAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDate.now();
  }
}