package com.health.fitness.domain;

import jakarta.persistence.*;
import lombok.*;

// DB와 1:1로 매핑 객체임을 선언
@Entity

@Table(name = "exercise")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Exercise {

    //테이블의 식별자인 Primary Key임을 나타냄
    @Id

    // 번호를 자동을 매겨 중복을 방지(AUTO_INCREMENT라도 생각하면 됨)
    // ex) 1, 2, 3... 번호를 자동으로 넣어줌
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 빈 값(NULL)을 허용하지 않음
    // 즉, DB컬럼에 NOT NULL 조건을 걸어둠
    @Column(nullable = false)
    private String name;

    // CHEST', 'BACK', 'LEG', 'SHOULDER', 'ARM' 외에는 데이터가 못들어오도록 설정
    @Column(columnDefinition = "ENUM('CHEST', 'BACK', 'LEG', 'SHOULDER', 'ARM')")

    // Enum 상수 이름을 문자열 그대로 저장
    @Enumerated(EnumType.STRING)
    private Part part;

    // 일반 문자열보다 더 긴 텍스트를 저장할 수 있도록 함
    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @Column(name = "youtube_search_keyword")
    private String youtubeSearchKeyword = "";


    //부위를 위한 Enum
    public enum Part {
        CHEST, BACK, LEG, SHOULDER, ARM
    }
}
