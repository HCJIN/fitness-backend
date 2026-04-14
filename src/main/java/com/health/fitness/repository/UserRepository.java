package com.health.fitness.repository;

import com.health.fitness.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository<User, Long> 상속만 해도
// save(), findById(), findAll(), delete() 등 기본 CRUD가 자동으로 생김
// Long → User의 PK 타입
public interface UserRepository extends JpaRepository<User, Long> {

  // 메서드 이름만 보고 JPA가 자동으로 SQL을 만들어줌
  // findByEmail → SELECT * FROM users WHERE email = ? 로 변환됨
  // Optional → 결과가 없을 수도 있음 (없으면 empty, 있으면 User 담김)
  Optional<User> findByEmail(String email);
}