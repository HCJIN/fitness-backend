package com.health.fitness.controller;

// ApiResponse : 모든 API 응답을 { success, data, message } 형식으로 통일해주는 공통 응답 클래스
import com.health.fitness.common.ApiResponse;

// ExerciseResponseDto : 클라이언트에게 반환할 데이터만 골라 담은 그릇 (엔티티 직접 노출 방지)
import com.health.fitness.dto.ExerciseResponseDto;

// Part : 운동 부위를 나타내는 Enum (CHEST, BACK, LEG, SHOULDER, ARM)
import com.health.fitness.domain.Exercise.Part;

// ExerciseService : 실제 비즈니스 로직(DB 조회 등)을 처리하는 서비스
import com.health.fitness.service.ExerciseService;

// @RequiredArgsConstructor 를 쓰기 위한 Lombok import
import lombok.RequiredArgsConstructor;

// ResponseEntity : HTTP 상태코드(200, 404 등)와 응답 바디를 함께 반환할 수 있는 Spring 클래스
import org.springframework.http.ResponseEntity;

// @RestController, @RequestMapping, @GetMapping, @RequestParam, @PathVariable
// REST API 관련 어노테이션들을 한번에 import
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController
// 이 클래스가 REST API 요청을 받는 컨트롤러임을 선언
// @Controller + @ResponseBody 가 합쳐진 것
// → 반환값을 JSON으로 자동 변환해서 응답해줌
@RestController

// @RequestMapping
// 이 컨트롤러의 모든 API URL 앞에 /api/exercises 를 자동으로 붙여줌
// ex) @GetMapping("/{id}") → 실제 URL은 /api/exercises/{id}
@RequestMapping("/api/exercises")

// @RequiredArgsConstructor
// final 필드(ExerciseService)를 자동으로 생성자 주입해줌
// new ExerciseService() 처럼 직접 만들지 않아도 Spring이 알아서 넣어줌
@RequiredArgsConstructor
public class ExerciseController {

  // ExerciseService를 주입받아서 비즈니스 로직을 위임
  // Controller는 "요청을 받고 응답을 돌려주는 것"만 담당
  // 실제 DB 조회나 데이터 처리는 Service에게 맡김 → 역할 분리
  private final ExerciseService exerciseService;

  // GET /api/exercises?part=CHEST 요청을 처리하는 메서드
  //
  // @GetMapping
  // HTTP GET 방식의 요청을 이 메서드가 받겠다고 선언
  // (GET = 데이터를 조회할 때 사용하는 HTTP 메서드)
  //
  // ResponseEntity<ApiResponse<List<ExerciseResponseDto>>>
  // - ResponseEntity : HTTP 상태코드(200 OK 등)를 함께 담아서 반환
  // - ApiResponse    : { success, data, message } 형식으로 한번 더 감쌈
  // - List<ExerciseResponseDto> : 실제 응답 데이터 (운동 목록)
  @GetMapping
  public ResponseEntity<ApiResponse<List<ExerciseResponseDto>>> getExercisesByPart(

      // @RequestParam : URL 뒤에 붙는 쿼리스트링 값을 받아옴
      // ex) GET /api/exercises?part=CHEST → part 변수에 CHEST가 담김
      // Spring이 문자열 "CHEST"를 자동으로 Part.CHEST Enum으로 변환해줌
      @RequestParam Part part) {

    // Service에게 해당 부위의 운동 목록 조회를 위임
    List<ExerciseResponseDto> exercises = exerciseService.getExercisesByPart(part);

    // ResponseEntity.ok() : HTTP 200 OK 상태코드와 함께 응답
    // ApiResponse.ok()    : { success: true, data: [...], message: "ok" } 형식으로 감싸서 반환
    return ResponseEntity.ok(ApiResponse.ok(exercises));
  }

  // GET /api/exercises/{id} 요청을 처리하는 메서드
  //
  // @GetMapping("/{id}")
  // URL 경로에 {id} 자리에 숫자가 오는 GET 요청을 이 메서드가 받겠다고 선언
  // ex) GET /api/exercises/1
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ExerciseResponseDto>> getExerciseById(

      // @PathVariable : URL 경로에 있는 {id} 값을 받아옴
      // ex) /api/exercises/1 → id 변수에 1L(Long 타입)이 담김
      // @RequestParam 은 ?key=value 형식, @PathVariable 은 /경로/값 형식
      @PathVariable Long id) {

    // Service에게 해당 id의 운동 단건 조회를 위임
    // id가 존재하지 않으면 Service에서 예외를 던짐
    ExerciseResponseDto exercise = exerciseService.getExerciseById(id);

    return ResponseEntity.ok(ApiResponse.ok(exercise));
  }
}