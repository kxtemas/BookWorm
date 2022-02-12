package com.gcu.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gcu.business.SecurityBusinessService;

/**
 * Date: 02/05/2022
 * Security Configuration for the Spring Boot Application. 
 * 
 * @author Micahel Mohler
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{ 
	//Used for encryption
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	@Bean
	BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	//Used to access user in database
	@Autowired
	SecurityBusinessService service;
	
	/**
	 * Sets up the security for the application. What is whitelisted, where the user is taken, what is the name of the login page,e tc
	 * 
	 * @param http Security for Spring Boot
	 * 
	 *  
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		
		http.csrf().disable()
		.httpBasic()
			.and()
			.authorizeRequests()
			.antMatchers("/service/**").authenticated()
			.and()
		.authorizeRequests()	
			.antMatchers("/", "/images/**", "/css/**", "/register/**", "/logout", "/goLogin", "/goRegister", "/displayOauthCode").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.usernameParameter("username")
			.passwordParameter("password")
			.permitAll()
			.defaultSuccessUrl("/shelf/", true)
			.and()
		.logout()
			.logoutUrl("/logout")
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.permitAll()
			.logoutSuccessUrl("/");
	}
	
	
	/**
	 * Insert the username from the service and tell it which encryption is being used for the password.
	 * 
	 * @param auth security authorization for the application
	 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{


		auth 
		.userDetailsService(service)
		.passwordEncoder(passwordEncoder);
			
	}
}
