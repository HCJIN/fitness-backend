package com.health.fitness.domain;

import jakarta.persistence.*;
import lombok.Getter;

// @Entity → 이 클래스가 DB 테이블이랑 연결된다고 Spring에 알려줌
// JPA가 이 클래스를 보고 자동으로 SQL을 만들어줌
@Entity

// @Table → 연결할 DB 테이블 이름 지정
// 안 쓰면 클래스 이름(User)이 테이블명이 되는데 명시적으로 써주는 게 좋음
@Table(name = "users")

// @Getter → 모든 필드의 getter 자동 생성 (Lombok)
// getEmail(), getPassword(), getNickname() 등이 자동으로 생김
@Getter
public class User {

  // @Id → 이 필드가 PK(기본키)임을 선언
  // @GeneratedValue → PK 값을 DB가 자동으로 생성 (AUTO_INCREMENT)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // @Column(unique = true) → DB에서 이 컬럼에 중복값 못 들어오게 제약 추가
  // nullable = false → NULL 값 허용 안 함
  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  private String nickname;

  // DEFAULT 0 → 가입 시 포인트 0으로 시작
  @Column(columnDefinition = "INT DEFAULT 0")
  private int point;

  @Column(columnDefinition = "INT DEFAULT 1")
  private int level;

  // USER / ADMIN 두 가지 역할
  @Column(columnDefinition = "VARCHAR(10) DEFAULT 'USER'")
  private String role;
}