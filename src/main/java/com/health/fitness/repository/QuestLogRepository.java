package com.health.fitness.repository;

import com.health.fitness.domain.Quest;
import com.health.fitness.domain.QuestLog;
import com.health.fitness.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface QuestLogRepository extends JpaRepository<QuestLog, Long> {

  // 중복 완료 방지: 같은 유저가 같은 퀘스트를 같은 날 완료했는지
  boolean existsByUserAndQuestAndDate(User user, Quest quest, LocalDate date);

  // 오늘 완료한 퀘스트 목록 (today API용)
  List<QuestLog> findByUserAndDate(User user, LocalDate date);
}