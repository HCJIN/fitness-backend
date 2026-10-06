package com.health.fitness.repository;

import com.health.fitness.domain.Quest;
import com.health.fitness.domain.User;
import com.health.fitness.domain.UserQuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {

    //findByUserAndQuest : SELECT * FROM user_quest WHERE user_id = ? AND quest_id = ?
    //이런 식으로 자동으로 쿼리를 만들줌
    Optional<UserQuest> findByUserAndQuest(User user, Quest quest);

    List<UserQuest> findByUser(User user);
}
