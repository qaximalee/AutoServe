package com.balti.autoserve.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(authProvider());
		//auth.userDetailsService(userDetailsService);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/rest_admin/**").hasAnyRole("ADMIN", "REST_ADMIN")
			.antMatchers("/counter/**").hasAnyRole("ADMIN", "REST_ADMIN", "COUNTER")
			.antMatchers("/kitchen/**").hasAnyRole("ADMIN", "REST_ADMIN", "COUNTER", "KITCHEN")
			.antMatchers("/user/get/**").hasAnyRole("ADMIN", "REST_ADMIN", "USER")
			.antMatchers("/user/add/**").hasAnyRole("ADMIN", "USER")
			.antMatchers("/user/update/**").hasAnyRole("ADMIN", "USER")
			.antMatchers("/user/delete/**").hasAnyRole("ADMIN", "USER")
			.and()
			.httpBasic()
			.and()
			.formLogin()
			.and()
			.csrf().disable();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(encoder());
	    return authProvider;
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*@Bean 
	public PasswordEncoder encode() {
		return NoOpPasswordEncoder.getInstance();
	}*/
}
