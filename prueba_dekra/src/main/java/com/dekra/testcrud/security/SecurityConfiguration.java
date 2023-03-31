package com.dekra.testcrud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() throws Exception {

		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(
				User.withUsername("user")
					.password(passwordEncoder().encode("user"))
					.roles("USER")
					.build());
		manager.createUser(
				User.withUsername("admin")
					.password(passwordEncoder().encode("admin"))
					.roles("ADMIN").build());

		return manager;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.httpBasic()
		.and()
	    .authorizeRequests()
	    .antMatchers("/productos")
	    .hasRole("ADMIN")
	    .antMatchers("/productos/*")
	    .hasRole("ADMIN")
	    .antMatchers("/productos/taxes/*")
	    .hasRole("ADMIN")
	    .antMatchers(HttpMethod.POST, "/productos")
	    .hasRole("ADMIN")
	    .antMatchers(HttpMethod.PUT, "/productos")
	    .hasRole("ADMIN")
	    .antMatchers(HttpMethod.DELETE, "/productos")
	    .hasRole("ADMIN")
	    .and()
	    .csrf()
	    .disable()
	    .formLogin();

		return http.build();
	}

}
