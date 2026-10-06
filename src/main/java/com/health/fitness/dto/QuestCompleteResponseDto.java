package com.health.fitness.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestCompleteResponseDto {

  private Long questId;
  private int earnedPoint;    // 이번에 받은 포인트 (출석 퀘스트 진행 중이면 0)
  private int totalPoint;     // 지급 후 내 총 포인트
  private int progress;       // 현재 진행도 (출석 퀘스트용)
  private boolean cleared;    // 퀘스트 달성 여부
}