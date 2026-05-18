package com.health.fitness.controller;

import com.health.fitness.common.ApiResponse;
import com.health.fitness.domain.Exercise;
import com.health.fitness.domain.Exercise.Part;
import com.health.fitness.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController : 이 클래스가 REST API 요청을 받는 컨트롤러임을 선언
// @Controller + @ResponseBody가 합쳐진 것
// 즉, 반환값을 JSON으로 자동 변환해서 응답해줌
@RestController

// @RequestMapping : 이 컨트롤러의 모든 API URL 앞에 /api/exercises 를 붙여줌
@RequestMapping("/api/exercises")

// @RequiredArgsConstructor : final 필드를 자동으로 생성자 주입
@RequiredArgsConstructor
public class ExerciseController {

  // Service를 주입받아서 비즈니스 로직을 위임
  private final ExerciseService exerciseService;

  // GET /api/exercises?part=CHEST 요청을 처리
  // @RequestParam : URL 뒤에 붙는 쿼리스트링 값을 받아옴
  // ex) ?part=CHEST → part 변수에 CHEST가 담김
  @GetMapping
  public ResponseEntity<ApiResponse<List<Exercise>>> getExercisesByPart(
      @RequestParam Part part) {

    List<Exercise> exercises = exerciseService.getExercisesByPart(part);

    // ApiResponse.success() : 공통 응답 포맷 { success, data, message } 로 감싸서 반환
    return ResponseEntity.ok(ApiResponse.success(exercises));
  }

  // GET /api/exercises/{id} 요청을 처리
  // @PathVariable : URL 경로에 있는 {id} 값을 받아옴
  // ex) /api/exercises/1 → id 변수에 1이 담김
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Exercise>> getExerciseById(
      @PathVariable Long id) {

    Exercise exercise = exerciseService.getExerciseById(id);

    return ResponseEntity.ok(ApiResponse.success(exercise));
  }
}