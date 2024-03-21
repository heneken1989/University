package com.aptech.group3.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aptech.group3.service.JwtAuthenticationFilter;
import com.aptech.group3.service.UserService;

@Configuration
@EnableWebSecurity
public class SercurityConfig      {
  
    
    @Autowired
    UserService userService;
    
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
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //  .csrf().disable()  // b? x?c th?c token ?? d? test
                //   .cors()
                //   .and()
                .authorizeHttpRequests((requests) -> requests
                               .requestMatchers("/test", "/login","/listCate", "/Shop/**", "/signup", "/css/**", "/fonts/**", "/image/**", "/images/**", "/js/**", "/vendor/**").permitAll()
                               .anyRequest().permitAll()
               // .anyRequest().permitAll()
            )


                .formLogin((form) -> form
                                .loginPage("/login")      //lay from login tu tao ra
                                .permitAll()
                                .defaultSuccessUrl("/index")    // neu ??ng nh?p ??ng th? tr? v? trang index
                                .failureUrl("/login?sucess=false")  // n?u ??ng nh?p sai th? ...
                                .loginProcessingUrl("/j_spring_security_check")    // d?ng n?y pase l?n from th:action
                )
                .logout((logout) -> logout
                                .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Enable CSRF protection
			        //  .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())  // Store CSRF token in a cookie
			        // .and()
			        // .cors();
		
		// Thêm một lớp Filter kiểm tra jwt
       http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
//      @Autowired
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception
//	{
//		 auth.userDetailsService(customerUserDetailService).passwordEncoder(passwordEncoder());
//	}
//      
	 @Autowired
	    void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
	        builder.userDetailsService(userService)
	                .passwordEncoder(new BCryptPasswordEncoder());
	    }
      
  

}
