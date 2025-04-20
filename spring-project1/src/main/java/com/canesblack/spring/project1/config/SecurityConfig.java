package com.canesblack.spring.project1.config;

import com.canesblack.spring.project1.controller.PageController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity 
//SpringSecurity 기능을 사용하려면 이 어노테이션을 써줘야한다.
public class SecurityConfig {

   


@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	//스프링시큐리티 기능을 사용하고자 할 때 메서드안에 작성한다.

	http.formLogin(login -> 
		login.loginPage("/loginPage") // URL to navigate to login page
		.loginProcessingUrl("/login") // Login processing URL
		.failureUrl("/loginPage?error=true") // URL to redirect on login failure
		.usernameParameter("username") // Username parameter name
		.passwordParameter("password") // Password parameter name
		.successHandler(authenticationSuccessHandler()) // Success handler
	);

	return http.build();
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
