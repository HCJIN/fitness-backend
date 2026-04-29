package com.health.fitness.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA를 위한 기본 생성자
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String nickname;

    @Builder.Default // 빌더 사용 시에도 기본값을 유지하게 함
    @Column(columnDefinition = "INT DEFAULT 0")
    private int point = 0;

    @Builder.Default // 빌더 사용 시에도 기본값을 유지하게 함
    @Column(columnDefinition = "INT DEFAULT 1")
    private int level = 1;

    @Builder.Default
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'USER'")
    private String role = "USER"; // 유저 : USER / 관리자 : admin

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }
}
