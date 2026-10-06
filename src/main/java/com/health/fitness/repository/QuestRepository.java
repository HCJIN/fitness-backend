package com.health.fitness.repository;

import com.health.fitness.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {

}
