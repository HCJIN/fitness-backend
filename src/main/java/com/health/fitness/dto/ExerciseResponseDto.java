package com.health.fitness.dto;

import com.health.fitness.domain.Exercise;
import com.health.fitness.domain.Exercise.Part;
import lombok.Getter;

// DTO (Data Transfer Object) : 클라이언트에게 응답할 때 필요한 데이터만 담는 그릇
// 엔티티(Exercise)를 그대로 반환하면 나중에 필드 추가/삭제 시 API가 바뀌어버림
// DTO로 한번 감싸면 엔티티가 바뀌어도 API 응답 형식은 유지할 수 있음
@Getter
public class ExerciseResponseDto {

  private Long id;
  private String name;
  private Part part;
  private String description;
  private String youtubeSearchKeyword;

  // Exercise 엔티티를 받아서 DTO로 변환하는 생성자
  // 컨트롤러나 서비스에서 new ExerciseResponseDto(exercise) 로 간단하게 변환 가능
  public ExerciseResponseDto(Exercise exercise) {
    this.id = exercise.getId();
    this.name = exercise.getName();
    this.part = exercise.getPart();
    this.description = exercise.getDescription();
    this.youtubeSearchKeyword = exercise.getYoutubeSearchKeyword();
  }
}