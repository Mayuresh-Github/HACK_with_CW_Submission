package com.server.attendance.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilter(requestHeaderAuthenticationFilter()).httpBasic().disable().formLogin().disable().csrf().disable();
	}

	@Bean
	public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter() throws Exception {
		RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
		requestHeaderAuthenticationFilter.setPrincipalRequestHeader("X-AUTH-TOKEN");
		requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager());
		requestHeaderAuthenticationFilter.setExceptionIfHeaderMissing(false);

		return requestHeaderAuthenticationFilter;
	}

}
