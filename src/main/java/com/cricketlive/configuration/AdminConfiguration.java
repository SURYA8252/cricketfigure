package com.cricketlive.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class AdminConfiguration{
	@Autowired
	private CustomAdminDetailService customAdminDetailService;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    public static final String[] PUBLIC_URLS = {
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
    @Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf().disable()
	        .authorizeHttpRequests()
	        .antMatchers("/api/auth/login").permitAll()
	        .antMatchers(PUBLIC_URLS).permitAll()
	        .antMatchers(HttpMethod.GET).permitAll()
	        .anyRequest()
	        .authenticated()
	        .and()
	        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
	        .and()
	        .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	    http.authenticationProvider(daoAuthenticationProvider());
		return http.build();
	}
    @Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
    @Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customAdminDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
    @Bean
    public FilterRegistrationBean<?> coresFilter() {
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	CorsConfiguration configuration = new CorsConfiguration();
    	configuration.setAllowCredentials(true);
    	configuration.addAllowedOrigin("http://localhost:4200");
    	configuration.addAllowedHeader("Authorization");
    	configuration.addAllowedHeader("Content-Type");
    	configuration.addAllowedHeader("localhost:4200");
    	configuration.addAllowedHeader("Accept");
    	configuration.addAllowedMethod("POST");
    	configuration.addAllowedMethod("GET");
    	configuration.addAllowedMethod("PUT");
    	configuration.addAllowedMethod("DELETE");
    	configuration.addAllowedMethod("OPTIONS");
    	configuration.addAllowedMethod("PATCH");
    	configuration.addAllowedMethod("HEAD");
    	configuration.setMaxAge(3600L);
    	source.registerCorsConfiguration("/**", configuration);
    	FilterRegistrationBean<?> bean = new FilterRegistrationBean<>(new CorsFilter(source));
    	bean.setOrder(-110);
    	return bean;
    }
}
