package com.kindredgroup.unibetlivetest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin()
        	.and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/bets/add").permitAll()
            .and().authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/events").permitAll()
            .and().authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/events/*/selections").permitAll()
            .and().csrf().disable();
        return http.build();
    }

}