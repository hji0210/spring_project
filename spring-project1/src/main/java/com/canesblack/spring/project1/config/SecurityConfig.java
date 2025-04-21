package com.canesblack.spring.project1.config;

import com.canesblack.spring.project1.controller.PageController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // 올바른 import 추가

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    private final PageController pageController;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler, PageController pageController, CorsConfigurationSource corsConfigurationSource) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.pageController = pageController;
        this.corsConfigurationSource = corsConfigurationSource;
    }

		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http
				.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				// CSRF 해킹 기법으로부터 보호 조치
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				// CORS는 특정 서버로만 데이터를 넘길 수 있도록 설정
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
				// 세션 설정
				.authorizeHttpRequests(autz -> autz
					.requestMatchers("/", "/loginPage", "/logout", "/noticeCheckPage", "/register", "/menu/all").permitAll()
					.requestMatchers(HttpMethod.POST, "/login").permitAll()
					.requestMatchers("/resource/**", "/WEB-INF/**").permitAll()
					.requestMatchers("/noticeAdd", "/noticeModifyPage").hasAnyAuthority("ADMIN", "MANAGER")
					.requestMatchers(HttpMethod.POST, "/menu/add").hasAnyAuthority("ADMIN", "MANAGER")
					.requestMatchers(HttpMethod.POST, "/menu/update").hasAnyAuthority("ADMIN", "MANAGER")
					.requestMatchers(HttpMethod.DELETE, "/menu/delete").hasAnyAuthority("ADMIN", "MANAGER")
					.anyRequest().authenticated()
				)
				.formLogin(login -> 
					login.loginPage("/loginPage") // 로그인 페이지 URL
						.loginProcessingUrl("/login") // 로그인 처리 URL
						.failureUrl("/loginPage?error=true") // 로그인 실패 시 리다이렉트 URL
						.usernameParameter("username") // 사용자 이름 파라미터
						.passwordParameter("password") // 비밀번호 파라미터
						.successHandler(authenticationSuccessHandler()) // 성공 핸들러
						.permitAll() // 모든 사용자에게 로그인 페이지 접근 허용
				);


			    .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))	
				 .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트 URL
					.invalidateHttpSession(true) // 로그아웃 시 세션 무효화
					.deleteCookies("JSESSIONID") // 로그아웃 시 쿠키 삭제
					.logoutSuccessHandler((request, response, authentication) -> {
					.permitAll()
					
					);// 로그아웃 성공 시 리다이렉트 URL



			return http.build(); // Return the SecurityFilterChain
		}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)
        corsConfiguration.addAllowedOriginPattern("*"); // 모든 도메인 허용
        corsConfiguration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        corsConfiguration.addAllowedHeader("*"); // 모든 헤더 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // 올바른 클래스 사용
        source.registerCorsConfiguration("/**", corsConfiguration); // 모든 경로에 대해 CORS 설정 적용
        return source;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                HttpSession session = request.getSession(); // Retrieve session
                boolean isManager = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN")
                        || grantedAuthority.getAuthority().equals("MANAGER")); // Check if user is manager
                if (isManager) {
                    session.setAttribute("MANAGER", true); // Add isManager attribute to session
                }
                session.setAttribute("username", authentication.getName()); // Add username to session
                session.setAttribute("isAuthenticated", true); // Add isAuthenticated attribute to session
                response.sendRedirect(request.getContextPath() + "/"); // Redirect after successful login
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }
}