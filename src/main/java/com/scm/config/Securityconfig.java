package com.scm.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scm.services.impl.SecurityCustomUserDetailservice;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
public class Securityconfig {
	
	private SecurityCustomUserDetailservice userDetailService;
	
	public Securityconfig(SecurityCustomUserDetailservice userDetailService) {
		this.userDetailService = userDetailService;
	}
	
//	Configuration of authentication provider for Spring security
	
	@Bean
	 public AuthenticationProvider authenticationProvider() {		 
		 DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();	
		 
//		 User detail service object:
		 daoAuthenticationProvider.setUserDetailsService(userDetailService);
		 
//		 Password encoder details 		 
		 daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		 
		 
		 return daoAuthenticationProvider;		 
	 }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		
//		Done url configuration who going to public and private 
		httpSecurity.authorizeHttpRequests(authorise ->{
//			authorise.requestMatchers("/home","/register","/services").permitAll();
			authorise.requestMatchers("/user/**").authenticated();
			authorise.anyRequest().permitAll();
		});
		
//		form default login
		
		httpSecurity.formLogin(formLogin -> {
			
			formLogin.loginPage("/login");
			formLogin.loginProcessingUrl("/authenticate");
			formLogin.successForwardUrl("/user/dashbaord");
			formLogin.failureForwardUrl("/login?error=true");
			
			formLogin.usernameParameter("email");
			formLogin.passwordParameter("password");
			
			/*
			  formLogin.failureHandler(new AuthenticationFailureHandler() {
			  
			  @Override public void onAuthenticationFailure(HttpServletRequest request,
			  HttpServletResponse response, AuthenticationException exception) throws
			  IOException, ServletException { // TODO Auto-generated method stub
			  
			  } });
			  
			  formLogin.successHandler( new AuthenticationSuccessHandler() {
			  
			  @Override public void onAuthenticationSuccess(HttpServletRequest request,
			  HttpServletResponse response, Authentication authentication) throws
			  IOException, ServletException { // TODO Auto-generated method stub
			  
			  } });
			 */
			
			
			
		});
		
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
