package com.health.fitness.common;

import lombok.Getter;

// 모든 API 응답을 이 형식으로 통일
// { "success": true, "data": {...}, "message": "ok" }
@Getter
public class ApiResponse<T> {

  private final boolean success;
  private final T data;
  private final String message;

  // private 생성자 → 외부에서 new ApiResponse() 직접 못 만들게 막음
  // 아래 정적 메서드로만 생성하게 강제
  private ApiResponse(boolean success, T data, String message) {
    this.success = success;
    this.data = data;
    this.message = message;
  }

  // 성공 응답 — 데이터 있을 때
  // 사용: ApiResponse.ok(loginResponseDto)
  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>(true, data, "ok");
  }

  // 성공 응답 — 데이터 없을 때 (예: 로그아웃)
  // 사용: ApiResponse.ok()
  public static <T> ApiResponse<T> ok() {
    return new ApiResponse<>(true, null, "ok");
  }

  // 실패 응답 — 에러 메시지만 담을 때
  // 사용: ApiResponse.fail("이메일 또는 비밀번호가 올바르지 않습니다")
  public static <T> ApiResponse<T> fail(String message) {
    return new ApiResponse<>(false, null, message);
  }
}