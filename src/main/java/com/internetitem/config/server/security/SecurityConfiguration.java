package com.internetitem.config.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private SettingsUserDetailsService userDetailsService;

	@Autowired
	private HttpServletRequest request;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
            .authorizeRequests()
                .antMatchers("/services/**")
				.authenticated()
            .and()
            .httpBasic();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public PermissionSet permissionSet() {
		LoggedInUserDetails details = (LoggedInUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new PermissionSet(details, request.getRemoteHost());
	}

}
