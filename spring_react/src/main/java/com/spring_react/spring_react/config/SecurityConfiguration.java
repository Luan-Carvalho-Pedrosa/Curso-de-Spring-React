package com.spring_react.spring_react.config;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import com.spring_react.spring_react.model.entity.Usuario;
import com.spring_react.spring_react.model.repository.UsuarioRepository;

import org.springframework.security.core.userdetails.User;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {


	 @Autowired
    private UsuarioRepository usuarioRepository;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		return security.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/api/usuarios/autenticar").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
						.anyRequest()
						.authenticated())
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.httpBasic(Customizer.withDefaults())
				.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		return new InMemoryUserDetailsManager(this.loadUsers());
	}

	private List<UserDetails> loadUsers() {

		List<Usuario> usuarios = this.usuarioRepository.findAll();
		List<UserDetails> userDetails = new ArrayList<UserDetails>();

		for (Usuario u : usuarios) {
			UserDetails userDetail = User.builder()
					.username(u.getEmail())
					.password(passwordEncoder().encode(u.getSenha()))
					.roles("ROLES")
					.build();

			userDetails.add(userDetail);
		}

		return userDetails;
	}
		
	

}
