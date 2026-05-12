package com.health.fitness.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "exercise")
@Getter
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 빈 값(NULL)을 허용하지 않음
    @Column(nullable = false)
    private String name;

    // CHEST', 'BACK', 'LEG', 'SHOULDER', 'ARM' 외에는 데이터가 못들어오도록 설정
    @Column(columnDefinition = "ENUM('CHEST', 'BACK', 'LEG', 'SHOULDER', 'ARM')")
    private String part;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String youtube_search_keyword;
}
