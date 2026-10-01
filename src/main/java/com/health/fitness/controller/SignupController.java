package com.health.fitness.controller;

import com.health.fitness.dto.SignupRequestDto;
import com.health.fitness.dto.SignupResponseDto;
import com.health.fitness.service.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SignupController {
    private final SignupService signupService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto request) {

        SignupResponseDto response = signupService.signup(request);
        return ResponseEntity.ok(response);

    }
}
