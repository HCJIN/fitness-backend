package com.health.fitness.controller;

import com.health.fitness.common.ApiResponse;
import com.health.fitness.dto.MyQuestResponseDto;
import com.health.fitness.dto.QuestCompleteResponseDto;
import com.health.fitness.dto.QuestResponseDto;
import com.health.fitness.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quests")
@RequiredArgsConstructor
public class QuestController {

  private final QuestService questService;

  // 전체 퀘스트 목록
  @GetMapping
  public ResponseEntity<ApiResponse<List<QuestResponseDto>>> getAllQuests() {
    return ResponseEntity.ok(ApiResponse.ok(questService.getAllQuests()));
  }

  // 오늘 퀘스트 목록 (메인 홈용)
  // authentication.getName() → JwtFilter에서 넣은 이메일
  @GetMapping("/today")
  public ResponseEntity<ApiResponse<List<QuestResponseDto>>> getTodayQuests(
      Authentication authentication) {
    return ResponseEntity.ok(ApiResponse.ok(
        questService.getTodayQuests(authentication.getName())));
  }

  // 내 진행현황
  @GetMapping("/my")
  public ResponseEntity<ApiResponse<List<MyQuestResponseDto>>> getMyQuests(
      Authentication authentication) {
    return ResponseEntity.ok(ApiResponse.ok(
        questService.getMyQuests(authentication.getName())));
  }

  // 퀘스트 완료 → 포인트 지급
  @PostMapping("/{id}/complete")
  public ResponseEntity<ApiResponse<QuestCompleteResponseDto>> completeQuest(
      @PathVariable Long id,
      Authentication authentication) {
    return ResponseEntity.ok(ApiResponse.ok(
        questService.completeQuest(id, authentication.getName())));
  }
}