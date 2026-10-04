package com.health.fitness.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quest")
@Getter
@Setter

//파라미터가 없는 기본 생성자를 자동 생성
@NoArgsConstructor

//모든 필드를 파라미터로 받는 생성자를 자동 생성
@AllArgsConstructor
@Builder
public class Quest {

    //이 필드가 테이블의 기본 키(PK) 임을 나타냅니다. JPA에게 "이 값으로 각 row를 구분한다"고 알려주는 역할
    @Id

    //PK 값을 어떻게 생성할지 지정
    //DB의 auto-increment 기능
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //이 필드가 매핑될 컬럼의 제약조건을 지정
    //nullable : false는 DB의 NOT NULL 제약을 걸어 빈 값을 막음.
    //length : 100은 VARCHAR(100)처럼 문자열 최대 길이를 100자로 제한
    @Column(nullable = false, length = 100)
    private String title;

    //Enum 상수 이름을 문자열 그대로 저장
    @Enumerated(EnumType.STRING)

    //nullable = false는 DB의 NOT NULL 제약을 걸어 빈 값을 막음.
    @Column(nullable = false)
    private QuestType type;

    @Column(name = "required_count", nullable = false)
    private Integer requiredCount;

    @Column(name = "reward_point", nullable = false)
    private Integer rewardPoint;


    public enum QuestType{
        SIMPLE,         //단순완료
        ACCUMULATE,     //누적
        ATTENDANCE      //출석
    }
}
