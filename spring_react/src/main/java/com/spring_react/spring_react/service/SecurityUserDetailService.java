package com.spring_react.spring_react.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring_react.spring_react.model.entity.Usuario;
import com.spring_react.spring_react.model.repository.UsuarioRepository;

@Service
public class SecurityUserDetailService {

     @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    public List<UserDetails> loadUsers() {

        List<Usuario> usuarios = this.usuarioRepository.findAll();
        List<UserDetails> userDetails = new ArrayList<UserDetails>();

        for (Usuario u : usuarios) {
            UserDetails userDetail = this.generateUserDetail(u);
            userDetails.add(userDetail);
        }

        return userDetails;
    }

    public UserDetails loadUserByLogin(String login) {

        Usuario usuario = this.usuarioRepository.findByEmail(login).get();

        return this.generateUserDetail(usuario);
        
    }
    
    private UserDetails generateUserDetail(Usuario u) {
        UserDetails userDetail = User.builder()
        .username(u.getEmail())
        .password(passwordEncoder().encode(u.getSenha()))
        .roles("ROLES")
                .build();
        
        return userDetail;
        
    }


    

    
}
