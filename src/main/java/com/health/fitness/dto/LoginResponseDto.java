package com.health.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// Lombok: 모든 필드를 파라미터로 받는 생성자를 자동 생성
// → new LoginResponseDto("토큰값") 이렇게 바로 쓸 수 있음
@Getter
@AllArgsConstructor
public class LoginResponseDto {

  // 로그인 성공 시 프론트에 JWT 토큰 하나만 줌
  // 프론트는 이걸 받아서 이후 요청마다 Header에 붙여서 보냄
  private String token;
}