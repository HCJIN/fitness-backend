package com.health.fitness.config;

import com.health.fitness.filter.JwtFilter;
import com.health.fitness.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration          // "이 파일은 설정 파일이에요" 라고 Spring에게 알려줌
@EnableWebSecurity      // Spring Security 기능을 활성화함
@RequiredArgsConstructor // final 필드를 생성자로 자동 주입 (Lombok)
public class SecurityConfig {

  private final JwtFilter jwtFilter;

  @Bean // Spring이 이 메서드의 리턴값을 관리하게 함
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        // CORS 설정을 아래 corsConfigurationSource() Bean에서 읽어옴
        // 이 줄 없으면 React → Spring 요청 시 브라우저가 전부 차단함
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  // CORS(Cross-Origin Resource Sharing) 설정
  // 브라우저는 기본적으로 다른 출처(포트)로 요청을 보내는 걸 막음
  // React(localhost:5173) → Spring(localhost:8080) 은 포트가 달라서 막힘
  // 여기서 허용 목록을 등록해야 통신 가능
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration config = new CorsConfiguration();

    // 허용할 출처 (프론트 주소)
    // Vite 기본 포트가 5173 → React 개발 서버 주소
    // 배포할 때는 실제 도메인으로 교체 (ex: "https://fitness.com")
    config.setAllowedOrigins(List.of("http://localhost:5173"));

    // 허용할 HTTP 메서드
    // OPTIONS → 브라우저가 실제 요청 보내기 전에 먼저 보내는 사전 요청(preflight)
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

    // 허용할 요청 헤더
    // "*" → 모든 헤더 허용 (Authorization 헤더로 JWT 토큰 보내야 하니까 반드시 필요)
    config.setAllowedHeaders(List.of("*"));

    // true → 쿠키, Authorization 헤더 등 인증 정보 포함 허용
    // JWT를 Authorization 헤더로 보내려면 이 옵션이 true여야 함
    config.setAllowCredentials(true);

    // 위에서 만든 CORS 설정을 모든 URL 경로("/**")에 적용
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}