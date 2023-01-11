package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		      .mvcMatchers("/manage/login/**").permitAll()
		      .antMatchers("/customer/**").permitAll()
		      .antMatchers("/css/**").permitAll()
		      .anyRequest().authenticated()
		      .and()
		      .formLogin()
		      .loginPage("/manage/login").permitAll()
		      .and()
		      .logout()
		      .logoutRequestMatcher(new AntPathRequestMatcher("/manage/logout"))
		      .logoutUrl("/manage/logout")
		      .logoutSuccessUrl("/");
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
        .withUser("admin")
        .password(passwordEncoder().encode("123456"))
        .roles("USER");
	}

}
