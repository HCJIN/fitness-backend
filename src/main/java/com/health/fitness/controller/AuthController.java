package com.health.fitness.controller;

import com.health.fitness.dto.LoginRequestDto;
import com.health.fitness.dto.LoginResponseDto;
import com.health.fitness.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController → @Controller + @ResponseBody 합친 것
// 이 클래스의 모든 메서드가 JSON을 반환함
@RestController

// @RequestMapping → 이 컨트롤러의 모든 URL 앞에 /api/auth 를 붙임
// ex) login() 메서드의 실제 URL = /api/auth/login
@RequestMapping("/api/auth")

@RequiredArgsConstructor
public class AuthController {

  // AuthService를 자동으로 주입받음
  private final AuthService authService;

  // @PostMapping("/login") → POST /api/auth/login 요청을 이 메서드가 처리
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(
      // @Valid → LoginRequestDto 안에 있는 @Email, @NotBlank 검사를 실행
      // 검사 실패 시 400 Bad Request 자동 반환 (if문 안 써도 됨)
      // @RequestBody → HTTP 요청의 Body(JSON)를 LoginRequestDto 객체로 변환
      @Valid @RequestBody LoginRequestDto dto) {

    // Service에 실제 로직 위임
    LoginResponseDto response = authService.login(dto);

    // ResponseEntity.ok() → HTTP 200 OK 상태코드와 함께 response를 JSON으로 반환
    return ResponseEntity.ok(response);
  }

  // 로그아웃은 JWT 방식에서는 서버가 할 게 없음
  // 프론트에서 토큰을 삭제하면 끝 (서버는 상태를 저장 안 하니까)
  // 그래도 API 형식 맞추기 위해 200만 반환
  @PostMapping("/logout")
  public ResponseEntity<Void> logout() {
    return ResponseEntity.ok().build();
  }
}