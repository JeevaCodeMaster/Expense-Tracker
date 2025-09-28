package com.Xtend.Expense_Tracker.Security;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//cors

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.Xtend.Expense_Tracker.Service.MyUDS;
import com.Xtend.Expense_Tracker.Exception.CustomAccessDeniedHandler;
import com.Xtend.Expense_Tracker.Exception.CustomAuthEntryPoint;
import com.Xtend.Expense_Tracker.Filter.*;

@Configuration
public class SecurityConfig {
	
	
	@Autowired
	private MyUDS userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter; 
	
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	
	@Autowired 
	private CustomAuthEntryPoint customAuthEntryPoint;
	

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      
    	
    return	http
    		.cors(Customizer.withDefaults())
            .csrf(csrf->csrf.disable())
            
            .authorizeHttpRequests(request->request 
            		.requestMatchers(
            				"/favicon.ico",
            			    "/css/**",
            			    "/js/**",
            			    "/images/**",
            			    "/login.html",
            			    "/dashboard.html",
            			    "/register.html",
            			    "/auth/**"
            			   ,"/users/register","/auth/login"
            			).permitAll()
            	           
            		.anyRequest().authenticated())
            
               
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex->ex.accessDeniedHandler(customAccessDeniedHandler)
            		.authenticationEntryPoint(customAuthEntryPoint))

        .build();
    }
	
    @Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
			throws Exception {
		return config.getAuthenticationManager();
	}
	
	
	
	
//	db security configuration
	
	
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
	
	@Bean
	public AuthenticationProvider Ap() {
		
		DaoAuthenticationProvider DaoP = new DaoAuthenticationProvider();
		DaoP.setPasswordEncoder(new BCryptPasswordEncoder(12));
		DaoP.setUserDetailsService(userDetailsService);
		
		return DaoP;
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(List.of("http://localhost:8085")); // your frontend
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(List.of("*"));
	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}


	

}
