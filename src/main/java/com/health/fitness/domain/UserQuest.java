package com.health.fitness.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_quest")
@Getter
@Setter

//파라미터가 없는 기본생성자를 자동 생성
@NoArgsConstructor

//모든 필드를 파라미터로 받는 생성자를 자동 생성
@AllArgsConstructor
@Builder
public class UserQuest {

    //이 필드가 테이블의 기본 키(PK) 임을 나타냅니다. JPA에게 "이 값으로 각 row를 구분한다"고 알려주는 역할
    @Id

    //PK값을 어떻게 생성할지 지정
    //DB의 auto-increment 기능
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne : "여러 개의 UserQuest가 하나의 User 또는 Quest에 속한다"는 관계입니다. 한 사용자가 여러 퀘스트를 진행
    //fetch = FetchType.LAZY : UserQuest를 조회할 때 연관된 User/Quest를 바로 같이 가져오지 않고,
    // 실제로 .getUser()처럼 접근하는 시점에 그때 조회한다는 뜻.
    // 기본값인 EAGER로 두면 UserQuest 하나 조회할 때마다 불필요하게 User, Quest까지 매번 같이 조회해서 성능이 나빠질 수 있음.
    @ManyToOne(fetch = FetchType.LAZY)

    //@JoinColumn : 실제 DB에서 FK 역할을 하는 컬럼이 user_id라는 걸 지정.
    //User 객체 전체가 아니라 DB테이블 관점에서는 여전히 user_id라는 BIGINT 컬럼 하나로 저장
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //@ManyToOne : 여러 개의 UserQuest가 하나의 Quest에 속함
    //fetch = FetchType.LAZY : 실제로 .getQuest()처럼 접근하는 시점에 조회
    @ManyToOne(fetch = FetchType.LAZY)

    //@JoinColumn : 실제 DB에서 FK 역할을 하는 컬럼이 quest_id라는 걸 지정.
    @JoinColumn(name = "quest_id", nullable = false)
    private Quest quest;

    //nullable = false는 DB의 NOT NULL 제약을 걸어 빈 값을 막음.
    @Column(nullable = false)
    private int progress;

    @Column(nullable = false)
    private boolean cleared;

    @Column(name = "cleared_at")
    private LocalDateTime clearedAt;

    // 진행도 +1, 목표 횟수에 "처음" 도달한 순간이면 true 반환
    // SIMPLE: 매번 포인트 지급 / ATTENDANCE: true일 때만 포인트 지급 (서비스에서 판단)
    public boolean addProgress() {
        this.progress++;
        if (!this.cleared && this.progress >= this.quest.getRequiredCount()) {
            this.cleared = true;
            this.clearedAt = LocalDateTime.now();
            return true;
        }
        return false;
    }
}