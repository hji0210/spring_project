package com.canesblack.spring.project1.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 해킹 기법으로부터 보호치
                // => 나중에 따로 HTML,jsp,자바스크립트(프론트영역->백엔드 영역으로 데이터가 넘어갈 때) CSRF 보호 기능 토큰도 넣어놓을 것
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

                // CORS는 특정 서버로만 데이터를 넘길 수 있도록 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 세션 설정
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                // URL 권한 설정
                .authorizeHttpRequests(autz -> autz
                        .requestMatchers("/", "/loginPage", "/logout", "/noticeCheckPage", "/registerPage", "/menu/all")
                        .permitAll()  // 누구나 접근 허용
                        .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()  // POST 요청도 누구나 허용
                        .requestMatchers("/resources/**", "/WEB-INF/**").permitAll()  // 자원 파일에 접근 허용
                        .requestMatchers("/noticeAdd", "/noticeModifyPage").hasAnyAuthority("ADMIN", "MANAGER")  // 관리자, 매니저만 접근 허용
                        .requestMatchers(HttpMethod.POST, "/menu/add").hasAnyAuthority("ADMIN", "MANAGER")  // 메뉴 추가는 관리자, 매니저만 허용
                        .requestMatchers(HttpMethod.POST, "/menu/update").hasAnyAuthority("ADMIN", "MANAGER")  // 메뉴 수정은 관리자, 매니저만 허용
                        .requestMatchers(HttpMethod.DELETE, "/menu/delete").hasAnyAuthority("ADMIN", "MANAGER")  // 메뉴 삭제는 관리자, 매니저만 허용
                        .anyRequest().authenticated()  // 그 외의 요청은 인증된 사용자만 접근 허용
                        //로그인을 해야지만 접근이 가능하ㅔ끔!그렇기 떄문에 로그인 페이지로 자동 이동됩니다.
                )
                .formLogin(login -> login
                        .loginPage("/loginPage")  // 로그인 페이지 URL
                        .loginProcessingUrl("/login")  // 로그인 처리 URL
                        .failureUrl("/loginPage?error=true")  // 로그인 실패 시 리다이렉트 URL
                        .usernameParameter("username")  // 사용자 이름 파라미터
                        .passwordParameter("password")  // 비밀번호 파라미터
                        .successHandler(authenticationSuccessHandler())  // 로그인 성공 핸들러
                        .permitAll()  // 모든 사용자에게 로그인 페이지 접근 허용
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  // 로그아웃 요청 URL 매핑
                        .logoutSuccessUrl("/")  // 로그아웃 성공 시 리다이렉트 URL
                        .invalidateHttpSession(true)  // 로그아웃 시 세션 무효화 => 세션공간 안에 있던 데이터가 사라짐
                        .deleteCookies("JSESSIONID")  // 로그아웃 시 쿠키 삭제
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // 로그아웃 성공 시 추가 작업 수행 후 리다이렉트
                            response.sendRedirect(request.getContextPath() + "/");
                        })
                        .permitAll()  // 모든 사용자에게 로그아웃 접근 허용, 위 기능을 수행시키려면 이 메서드 실행
                );

        return http.build();  // SecurityFilterChain 반환
        //최종 http에 적용시킬 떄 사용하는 메서드
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);  // Allow credentials (cookies, authorization headers, etc.)
        corsConfiguration.addAllowedOriginPattern("*");  // 모든 도메인 허용
        corsConfiguration.addAllowedMethod("*");  // 모든 HTTP 메서드 허용
        corsConfiguration.addAllowedHeader("*");  // 모든 헤더 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);  // 모든 경로에 대해 CORS 설정 적용
        return source;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                HttpSession session = request.getSession();  // 세션 가져오기
                boolean isManager = authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN")
                                || grantedAuthority.getAuthority().equals("MANAGER"));  // 사용자가 ADMIN 또는 MANAGER 권한이 있는지 확인
                if (isManager) {
                    //세션에다가 로그인한 아이디를 저장한다.
                    session.setAttribute("MANAGER", true); 
                     // 관리자이면 세션에 MANAGER 속성 추가
                     //세션에다가 로그인됬나 여부를 저장
                }
                session.setAttribute("username", authentication.getName());  // 세션에 사용자 이름 저장
                session.setAttribute("isAuthenticated", true);  // 인증된 사용자임을 세션에 저장
                response.sendRedirect(request.getContextPath() + "/");  // 로그인 성공 후 홈으로 리다이렉트
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

    //스프링프레임워크의 비밀번호 기능능
    @Bean      
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  
    }
}
