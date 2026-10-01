package com.health.fitness.service;

import com.health.fitness.dto.LoginRequestDto;
import com.health.fitness.dto.LoginResponseDto;
import com.health.fitness.repository.UserRepository;  // 팀원이 만들 파일
import com.health.fitness.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// @Service → "이 클래스는 비즈니스 로직을 담당해요" 라고 Spring에 알려줌
// Spring이 이 클래스를 Bean으로 관리하기 시작함
@Service
// @RequiredArgsConstructor → final 필드들을 생성자로 자동 주입
// 아래 4개의 final 필드가 Spring에서 자동으로 들어옴
@RequiredArgsConstructor
public class AuthService {

  // UserRepository: DB에서 유저를 찾아오는 역할 (팀원이 만들 파일)
  private final UserRepository userRepository;

  // JwtUtil: 토큰 생성 / 검증 (네가 이미 만든 파일)
  private final JwtUtil jwtUtil;

  // PasswordEncoder: 비밀번호 암호화 / 비교 (SecurityConfig에서 Bean으로 등록했음)
  // DB에 저장된 암호화된 비밀번호랑 입력한 비밀번호를 비교할 때 씀
  private final PasswordEncoder passwordEncoder;

  // 로그인 메서드: LoginRequestDto를 받아서 LoginResponseDto를 돌려줌
  public LoginResponseDto login(LoginRequestDto dto) {

    // 1단계: 이메일로 유저를 DB에서 찾음
    // orElseThrow → 유저가 없으면 예외를 던짐 (→ 400 or 401 응답)
    var user = userRepository.findByEmail(dto.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

    // 2단계: 비밀번호 확인
    // passwordEncoder.matches(입력한 비번, DB에 저장된 암호화된 비번)
    // → 내부적으로 BCrypt 알고리즘으로 비교함 (단순 == 비교 절대 안 됨!)
    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
    }

    // 3단계: 이메일을 담아서 JWT 토큰 생성
    // 이 토큰이 이후 모든 요청의 신분증 역할을 함
    String token = jwtUtil.generateToken(user.getEmail());

    // 4단계: 토큰을 응답 DTO에 담아서 반환
    return new LoginResponseDto(token);
  }
}