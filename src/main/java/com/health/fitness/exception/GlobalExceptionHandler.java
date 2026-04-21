package com.health.fitness.exception;

import com.health.fitness.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice → 모든 컨트롤러에서 발생하는 예외를 여기서 일괄 처리
// 각 컨트롤러에 try-catch 안 써도 됨
@RestControllerAdvice
public class GlobalExceptionHandler {

  // @Valid 검증 실패 시 발생하는 예외 처리
  // 예: 이메일 형식 오류, 비밀번호 빈 값 등
  // → 400 Bad Request
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleValidation(
      MethodArgumentNotValidException e) {

    // 여러 검증 오류 중 첫 번째 메시지만 꺼냄
    String message = e.getBindingResult()
        .getFieldErrors()
        .get(0)
        .getDefaultMessage();

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.fail(message));
  }

  // 이메일 중복, 비밀번호 불일치 등 직접 던지는 예외 처리
  // Service에서 throw new IllegalArgumentException("이미 사용 중인 이메일입니다")
  // → 400 Bad Request
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(
      IllegalArgumentException e) {

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.fail(e.getMessage()));
  }

  // 로그인 실패 (인증 오류) 처리
  // Service에서 throw new IllegalStateException("이메일 또는 비밀번호가 올바르지 않습니다")
  // → 401 Unauthorized
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ApiResponse<Void>> handleIllegalState(
      IllegalStateException e) {

    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ApiResponse.fail(e.getMessage()));
  }

  // 위에서 처리 못 한 나머지 모든 예외
  // 예상 못 한 서버 오류 → 500 Internal Server Error
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.fail("서버 오류가 발생했습니다"));
  }
}