package com.health.fitness.dto;

import com.health.fitness.domain.Quest;
import com.health.fitness.domain.UserQuest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyQuestResponseDto {

  private Long questId;
  private String title;
  private Quest.QuestType type;
  private int progress;
  private Integer requiredCount;
  private boolean cleared;
  private LocalDateTime clearedAt;

  public static MyQuestResponseDto from(UserQuest userQuest) {
    Quest quest = userQuest.getQuest();
    return MyQuestResponseDto.builder()
        .questId(quest.getId())
        .title(quest.getTitle())
        .type(quest.getType())
        .progress(userQuest.getProgress())
        .requiredCount(quest.getRequiredCount())
        .cleared(userQuest.isCleared())
        .clearedAt(userQuest.getClearedAt())
        .build();
  }
}