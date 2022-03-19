package tn.com.cnamplus.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import tn.com.cnamplus.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private MyUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		auth.parentAuthenticationManager(authenticationManagerBean()).userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/registration").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/getUserById/{idUser}").access("hasRole('SUPERADMIN')")
		.antMatchers("/say**").access("hasRole('SUPERADMIN') or hasRole('ADMIN') ")
		.antMatchers("/getAll").access("hasRole('ADMIN')")
		.anyRequest().authenticated()
		.and()
		.httpBasic()
		.and()
		.csrf().disable();
	}
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
}
