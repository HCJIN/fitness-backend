package com.health.fitness.dto;

import com.health.fitness.domain.Quest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestResponseDto {

  private Long id;
  private String title;
  private Quest.QuestType type;
  private Integer requiredCount;
  private Integer rewardPoint;
  private boolean completedToday;   // 오늘 이미 완료했는지 (today API용)

  public static QuestResponseDto of(Quest quest, boolean completedToday) {
    return QuestResponseDto.builder()
        .id(quest.getId())
        .title(quest.getTitle())
        .type(quest.getType())
        .requiredCount(quest.getRequiredCount())
        .rewardPoint(quest.getRewardPoint())
        .completedToday(completedToday)
        .build();
  }
}