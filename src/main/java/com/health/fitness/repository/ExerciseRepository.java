package com.health.fitness.repository;

import com.health.fitness.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByPart(Exercise.Part part);
}
