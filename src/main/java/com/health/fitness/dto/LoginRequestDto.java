package com.health.fitness.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

// Lombok: getter 메서드를 자동으로 생성해줌 (getEmail(), getPassword())
@Getter
public class LoginRequestDto {

  // @Email → 이메일 형식이 아니면 400 Bad Request 자동으로 반환
  // @NotBlank → null이거나 공백이면 400 반환 (빈 문자열도 막음)
  @Email
  @NotBlank
  private String email;

  @NotBlank
  private String password;
}