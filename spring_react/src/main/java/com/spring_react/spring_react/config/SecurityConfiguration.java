package com.spring_react.spring_react.config;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import com.spring_react.spring_react.JwtTokkenFilter;
import com.spring_react.spring_react.model.entity.Usuario;
import com.spring_react.spring_react.model.repository.UsuarioRepository;
import com.spring_react.spring_react.service.JwtService;
import com.spring_react.spring_react.service.SecurityUserDetailService;

import jakarta.servlet.Filter;

import org.springframework.security.core.userdetails.User;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {


		@Autowired
		private SecurityUserDetailService securityUserDetailService;

		@Autowired
		private JwtService jwtService;
		
		

		@Bean
		public JwtTokkenFilter jwtTokkenFilter() {
				return new JwtTokkenFilter(jwtService, securityUserDetailService);
			
		}
		

	
		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
			return security.csrf(csrf -> csrf.disable())
					.authorizeHttpRequests(authorize -> authorize
							.requestMatchers(HttpMethod.POST, "/api/usuarios/autenticar").permitAll()
							.requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
							.anyRequest()
							.authenticated())
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					//.and().httpBasic(Customizer.withDefaults()).build();
					.and().addFilterBefore(jwtTokkenFilter(), UsernamePasswordAuthenticationFilter.class)
					.cors(Customizer.withDefaults())
					.build();
		}
		
		@Bean
		public UserDetailsService userDetailsService() {

			return new InMemoryUserDetailsManager(securityUserDetailService.loadUsers());
		}

		@Bean
		public UrlBasedCorsConfigurationSource corsConfigurationSource() {
			List<String> all = Arrays.asList("*");

			List<String> origins = Arrays.asList("http://localhost:3000");


			CorsConfiguration configuration = new CorsConfiguration();
			configuration.setAllowedOrigins(origins);
			configuration.setAllowedMethods(all);
			configuration.setAllowedHeaders(all);
			configuration.setAllowCredentials(true);
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", configuration);
			return source;
		}

		/* 
		@Bean
		public FilterRegistrationBean<CorsFilter> corsFilter() {
			List<String> all = Arrays.asList("*");
			
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedMethods(all);
			config.setAllowedOrigins(all);
			config.setAllowedHeaders(all);
			config.setAllowCredentials(true);

			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", config);


			

			CorsFilter corsFilter = new CorsFilter(source);

			FilterRegistrationBean<CorsFilter> filter
					= new FilterRegistrationBean<CorsFilter>(corsFilter);

			filter.setOrder(Ordered.HIGHEST_PRECEDENCE);

			return filter;
		}
			*/

		
		
	

}
