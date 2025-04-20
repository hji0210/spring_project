package com.canesblack.spring.project1.config;

import com.canesblack.spring.project1.controller.PageController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PageController pageController;

    public SecurityConfig(PageController pageController) {
        this.pageController = pageController;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .formLogin(login -> login
                .loginPage("/loginPage") // 사용자 정의 로그인 페이지
                .loginProcessingUrl("/login") // 로그인 form action
                .failureUrl("/loginPage?error=true") // 실패 시 리다이렉트
                .usernameParameter("username") // 아이디 파라미터
                .passwordParameter("password") // 비밀번호 파라미터
                .successHandler(authenticationSuccessHandler()) // 성공 시 핸들러
            );

        // 여기에 권한 설정 등 다른 설정도 추가 가능
        // 예: .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {

                HttpSession session = request.getSession(); // 세션 가져오기

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
}
