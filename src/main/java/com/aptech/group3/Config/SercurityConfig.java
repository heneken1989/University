package com.aptech.group3.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.aptech.group3.serviceImpl.CustomAccessDeniedHandler;
import com.aptech.group3.serviceImpl.CustomerUserDetailService;
import com.aptech.group3.serviceImpl.JwtAuthenticationFilter;
import com.aptech.group3.serviceImpl.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SercurityConfig      {

    @Autowired
    UserServiceImpl userService;
    
    @Autowired
    CustomerUserDetailService customerUserDetailService;
    
    @Bean
     BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
    
    @Bean
     AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
         return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    @Order(1)
    public SecurityFilterChain app(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/**")
                .authorizeHttpRequests(au -> {
                    au.requestMatchers(HttpMethod.GET, "/login", "/logout").permitAll();
                    au.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    au.anyRequest().permitAll();
                })
                .formLogin(frm -> {
                    frm.loginPage("/login")    
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll()
                    .defaultSuccessUrl("/index")   
                    .failureUrl("/login?sucess=false") ;
                })
                .logout(lo -> {
                    lo.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                      .logoutSuccessUrl("/login"); // Redirect after logout
                })
                .exceptionHandling(handling -> handling
                    .accessDeniedHandler(new CustomAccessDeniedHandler()) // Custom access denied handler
                )
                .csrf(csrf -> csrf.disable()) // CSRF protection as per your requirement
                .build();
    }
    
	@Bean
	@Order(2)
	public SecurityFilterChain api(HttpSecurity http) throws Exception {
	    return http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(au -> {
                    au.requestMatchers(HttpMethod.GET, "/api/login", "/api/logout").permitAll();
                    au.requestMatchers(HttpMethod.POST, "/api/login").permitAll();
                    au.requestMatchers("/api//admin/**").hasAuthority("ADMIN");
                    au.requestMatchers("/api/teacher/**").hasAuthority("TEACHER");
                    au.requestMatchers("/api/student/**").hasAuthority("STUDENT");
                    au.anyRequest().authenticated();
                })
                // Adding JWT filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable()) // Often, CSRF is disabled for APIs
                .build();
	}
	
	
	

	 @Autowired
	    void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
	        builder.userDetailsService(customerUserDetailService)
	                .passwordEncoder(new BCryptPasswordEncoder());
	    }

}
