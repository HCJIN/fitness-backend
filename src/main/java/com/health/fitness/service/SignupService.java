package com.health.fitness.service;

import com.health.fitness.dto.SignupRequestDto;
import com.health.fitness.dto.SignupResponseDto;
import com.health.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.health.fitness.domain.User;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponseDto signup(SignupRequestDto request) {

        if (request.getEmail().isBlank()) {
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        } else if (request.getPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        } else if (request.getNickname().isBlank()) {
            throw new IllegalArgumentException("닉네임을 입력해주세요.");
        } else {
            // 이메일 중복 확인
            userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
                throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
            });

            // 닉네임 중복 확인
            userRepository.findByNickname(request.getNickname()).ifPresent(user -> {
                throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
            });

            String encodePassword = passwordEncoder.encode(request.getPassword());

            User user = User.builder()
                    .email(request.getEmail())
                    .password(encodePassword) // 암호화된 비밀번호 저장
                    .nickname(request.getNickname())
                    .build();

            User savedUser = userRepository.save(user);

            // 저장된 엔티티를 응답 DTO로 변환 (비밀번호 제외!)
            return SignupResponseDto.builder()
                    .email(savedUser.getEmail())
                    .nickname(savedUser.getNickname())
                    .build();
        }


    }
}
