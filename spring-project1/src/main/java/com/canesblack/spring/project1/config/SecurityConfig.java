package com.canesblack.spring.project1.config;

import com.canesblack.spring.project1.SpringProject1Application;
import com.canesblack.spring.project1.controller.PageController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PageController pageController;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final CorsConfigurationSource corsConfigurationSource;
    private final SpringProject1Application springProject1Application;

    public SecurityConfig(PageController pageController,
                          AuthenticationSuccessHandler authenticationSuccessHandler,
                          CorsConfigurationSource corsConfigurationSource,
                          SpringProject1Application springProject1Application) {
        this.pageController = pageController;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.corsConfigurationSource = corsConfigurationSource;
        this.springProject1Application = springProject1Application;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf
                // CSRF 보호 조치 설정 (자바스크립트에도 연동 예정)
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            .cors(cors -> cors
                // cors는 특정 서버로만 데이터를 넘길 수 있도록 설정
                .configurationSource(corsConfigurationSource)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .authorizeHttpRequests(auth -> auth
                // 인증 없이 접근 가능한 URL
                .requestMatchers("/", "/loginPage", "/logout", "/noticeCheckPage", "/register", "/menu/all").permitAll()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers("/resources/**", "/WEB-INF/**").permitAll()
                // 특정 권한이 필요한 URL
                .requestMatchers("/noticeAdd", "/noticeModifyPage").hasAnyAuthority("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.POST, "/menu/add").hasAnyAuthority("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.POST, "/menu/update").hasAnyAuthority("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.POST, "/menu/delete").hasAnyAuthority("ADMIN", "MANAGER")
                // 그 외 모든 요청은 인증 필요
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/loginPage") // 사용자 정의 로그인 페이지
                .loginProcessingUrl("/login") // 로그인 form action
                .failureUrl("/loginPage?error=true") // 실패 시 리다이렉트
                .usernameParameter("username") // 아이디 파라미터
                .passwordParameter("password") // 비밀번호 파라미터
                .successHandler(authenticationSuccessHandler()) // 성공 시 핸들러
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // 로그아웃 URL
                .logoutSuccessUrl("/loginPage") // 로그아웃 성공 시 이동
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 삭제
                .permitAll()
            );

        return http.build();
    }

    // 로그인 성공 시 동작 정의
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {

                HttpSession session = request.getSession(); // 세션 가져오기

                // 관리자 또는 매니저 여부 확인
                boolean isManager = authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority ->
                                grantedAuthority.getAuthority().equals("ADMIN") ||
                                grantedAuthority.getAuthority().equals("MANAGER"));

                if (isManager) {
                    session.setAttribute("MANAGER", true);
                }

                session.setAttribute("username", authentication.getName());
                session.setAttribute("isAuthenticated", true);

                // 로그인 성공 후 리다이렉트
                response.sendRedirect(request.getContextPath() + "/");

                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

    // CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:3030"));
        // 프론트단: localhost:3030, 백엔드단: localhost:8080
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용
        return source;
    }

}
