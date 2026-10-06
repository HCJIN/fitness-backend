package com.health.fitness.exception;

// 같은 날 같은 퀘스트를 두 번 완료하려 할 때 → 409 Conflict
public class DuplicateQuestException extends RuntimeException {

  public DuplicateQuestException(String message) {
    super(message);
  }
}