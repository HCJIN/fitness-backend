package com.health.fitness.service;

import com.health.fitness.domain.Exercise.Part;
import com.health.fitness.dto.ExerciseResponseDto;
import com.health.fitness.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// @Service : 이 클래스가 "비즈니스 로직을 처리하는 서비스야" 라고 Spring에게 알려주는 표시
// Spring이 이 클래스를 자동으로 Bean으로 등록해줌
// Controller → Service → Repository 흐름에서 중간 역할
@Service

// @RequiredArgsConstructor : final로 선언된 필드를 자동으로 생성자 주입해줌
// 즉, ExerciseRepository를 new로 직접 만들지 않아도 Spring이 알아서 넣어줌
@RequiredArgsConstructor
public class ExerciseService {

  // final : 한번 주입받으면 바꿀 수 없음 (불변)
  // @RequiredArgsConstructor가 이 필드를 보고 생성자를 자동으로 만들어줌
  private final ExerciseRepository exerciseRepository;

  // 부위별 운동 목록을 반환하는 메서드
  // Part : Exercise 엔티티 안에 정의한 Enum (CHEST, BACK, LEG, SHOULDER, ARM)
  // List<ExerciseResponseDto> : 운동 여러 개를 DTO로 변환해서 리스트로 반환
  //
  // ❓ 왜 Exercise 엔티티 그대로 반환 안 하고 DTO로 변환하나요?
  // → 엔티티를 그대로 반환하면 DB 구조가 API에 그대로 노출됨
  // → 나중에 엔티티 필드가 바뀌면 API 응답도 같이 바뀌어버려서 프론트가 망가짐
  // → DTO로 한번 감싸면 엔티티가 바뀌어도 API 응답 형식은 유지할 수 있음
  public List<ExerciseResponseDto> getExercisesByPart(Part part) {

    // stream() : 리스트를 하나씩 꺼내서 처리할 수 있게 해주는 Java 기능
    // .map() : 각각의 Exercise 엔티티를 ExerciseResponseDto로 변환
    //          ExerciseResponseDto::new 는 new ExerciseResponseDto(exercise) 와 같은 의미
    // .collect(Collectors.toList()) : 변환된 것들을 다시 List로 모아줌
    //
    // 쉽게 말하면 :
    // [Exercise, Exercise, Exercise] 리스트를
    // [ExerciseResponseDto, ExerciseResponseDto, ExerciseResponseDto] 리스트로 바꾸는 것
    return exerciseRepository.findByPart(part)
        .stream()
        .map(ExerciseResponseDto::new)
        .collect(Collectors.toList());
  }

  // 운동 상세 정보를 반환하는 메서드
  // id로 특정 운동 하나를 찾아서 DTO로 변환 후 반환
  public ExerciseResponseDto getExerciseById(Long id) {

    // findById() : JpaRepository가 기본으로 제공하는 메서드
    // Optional로 감싸져서 반환되기 때문에 .orElseThrow()로 없으면 예외 던짐
    // 즉, 없는 id로 요청하면 "운동을 찾을 수 없습니다" 에러 반환
    return exerciseRepository.findById(id)
        .map(ExerciseResponseDto::new)
        .orElseThrow(() -> new IllegalArgumentException("운동을 찾을 수 없습니다."));
  }
}