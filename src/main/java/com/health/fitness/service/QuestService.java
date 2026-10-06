package com.health.fitness.service;

import com.health.fitness.domain.Quest;
import com.health.fitness.domain.QuestLog;
import com.health.fitness.domain.User;
import com.health.fitness.domain.UserQuest;
import com.health.fitness.dto.MyQuestResponseDto;
import com.health.fitness.dto.QuestCompleteResponseDto;
import com.health.fitness.dto.QuestResponseDto;
import com.health.fitness.exception.DuplicateQuestException;
import com.health.fitness.repository.QuestLogRepository;
import com.health.fitness.repository.QuestRepository;
import com.health.fitness.repository.UserQuestRepository;
import com.health.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)   // 기본은 조회 전용, 데이터를 바꾸는 메서드만 따로 @Transactional
public class QuestService {

  private final QuestRepository questRepository;
  private final UserQuestRepository userQuestRepository;
  private final QuestLogRepository questLogRepository;
  private final UserRepository userRepository;

  // GET /api/quests — 전체 퀘스트 목록
  public List<QuestResponseDto> getAllQuests() {
    return questRepository.findAll().stream()
        .map(quest -> QuestResponseDto.of(quest, false))
        .toList();
  }

  // GET /api/quests/today — 오늘 퀘스트 목록 + 오늘 완료 여부
  public List<QuestResponseDto> getTodayQuests(String email) {
    User user = findUser(email);

    // 오늘 완료한 퀘스트 id 모음
    Set<Long> completedTodayIds = questLogRepository
        .findByUserAndDate(user, LocalDate.now()).stream()
        .map(log -> log.getQuest().getId())
        .collect(Collectors.toSet());

    return questRepository.findAll().stream()
        .map(quest -> QuestResponseDto.of(quest, completedTodayIds.contains(quest.getId())))
        .toList();
  }

  // GET /api/quests/my — 내 퀘스트 진행현황
  public List<MyQuestResponseDto> getMyQuests(String email) {
    User user = findUser(email);
    return userQuestRepository.findByUser(user).stream()
        .map(MyQuestResponseDto::from)
        .toList();
  }

  // POST /api/quests/{id}/complete — 퀘스트 완료 처리 + 포인트 지급
  // 아래 작업이 하나의 트랜잭션: 중간에 실패하면 전부 롤백
  @Transactional
  public QuestCompleteResponseDto completeQuest(Long questId, String email) {
    User user = findUser(email);
    Quest quest = questRepository.findById(questId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀘스트입니다."));
    LocalDate today = LocalDate.now();

    // 1. 중복 완료 방지 → 409
    if (questLogRepository.existsByUserAndQuestAndDate(user, quest, today)) {
      throw new DuplicateQuestException("오늘 이미 완료한 퀘스트입니다.");
    }

    // 2. 완료 기록 저장
    questLogRepository.save(QuestLog.builder()
        .user(user)
        .quest(quest)
        .date(today)
        .count(1)
        .actionType(quest.getType() == Quest.QuestType.ATTENDANCE
            ? QuestLog.ActionType.ATTENDANCE
            : QuestLog.ActionType.EXERCISE)
        .build());

    // 3. 진행도 갱신 (처음 하는 퀘스트면 새로 생성)
    UserQuest userQuest = userQuestRepository.findByUserAndQuest(user, quest)
        .orElseGet(() -> userQuestRepository.save(UserQuest.builder()
            .user(user)
            .quest(quest)
            .progress(0)
            .cleared(false)
            .build()));

    boolean justCleared = userQuest.addProgress();

    // 4. 포인트 지급
    //    SIMPLE: 완료할 때마다 지급
    //    ATTENDANCE / ACCUMULATE: 목표 횟수에 처음 도달한 순간에만 지급
    int earnedPoint = 0;
    if (quest.getType() == Quest.QuestType.SIMPLE || justCleared) {
      earnedPoint = quest.getRewardPoint();
      user.addPoint(earnedPoint);
    }
    // user, userQuest는 트랜잭션 안에서 조회한 객체라 save() 없이도 변경 내용이 자동 반영됨 (변경 감지)

    return QuestCompleteResponseDto.builder()
        .questId(quest.getId())
        .earnedPoint(earnedPoint)
        .totalPoint(user.getPoint())
        .progress(userQuest.getProgress())
        .cleared(userQuest.isCleared())
        .build();
  }

  private User findUser(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
  }
}