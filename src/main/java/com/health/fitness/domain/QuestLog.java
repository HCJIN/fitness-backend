package com.health.fitness.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "quest_log")
@Getter
@Setter

//파라미터가 없는 기본생성자를 자동 생성
@NoArgsConstructor

//모든 필드를 파라미터로 받는 생성자를 자동 생성
@AllArgsConstructor
@Builder
public class QuestLog {

    //이 필드가 테이블의 기본 키(PK)임을 나타냄.
    @Id

    //DB의 Auto-increment 기능
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //여러 개의 QuestLog가 하나의 User에 속함.
    //LAZY : user정보가 실제로 필요할 때(.getUser() 호출 시점)만 DB조회
    @ManyToOne(fetch = FetchType.LAZY)

    //@JoinColumn : 실제 DB에서 FK 역할을 하는 컬럼이 user_id라는 걸 지정.
    //User 객체 전체가 아니라 DB테이블 관점에서는 여전히 user_id라는 BIGINT 컬럼 하나로 저장
    //실제 DB 컬럼명은 user_id (BIGINT, FK -> users)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //여러 개의 QuestLog가 하나의 Quest에 속함.
    @ManyToOne(fetch = FetchType.LAZY)

    //@JoinColumn : 실제 DB에서 FK 역할을 하는 컬럼이 user_id라는 걸 지정.
    //User 객체 전체가 아니라 DB테이블 관점에서는 여전히 user_id라는 BIGINT 컬럼 하나로 저장
    //실제 DB 컬럼명은 quest_id (BIGINT, FK -> quest)
    @JoinColumn(name = "quest_id", nullable = false)
    private Quest quest;

    //기록 날짜
    //nullable = false는 DB의 NOT NULL 제약을 걸어 빈 값을 막음.
    @Column(nullable = false)
    private LocalDate date;

    //횟수
    @Column(nullable = false)
    private Integer count;

    //Enum 상수 이름 그대로 저장
    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private ActionType actionType;

    //기록 일시
    //실제 INSERT된 정확한 시각, 정렬/통계/중복 방지에 사용
    //INSERT가 되는 순간의 시각을 Hibernate가 자동으로 채워줌(DEFAULT CURRENT_TIMESTAMP 역할)
    @CreationTimestamp

    //updatable = false : 이후 row가 수정되어도 created_at 값은 절대 바뀌지 않도록 고정
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public enum ActionType {
        ATTENDANCE,     //출석
        EXERCISE,       //운동
        ROUTINE         //루틴
    }
}
