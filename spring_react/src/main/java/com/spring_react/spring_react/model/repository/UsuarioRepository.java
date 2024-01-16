package com.spring_react.spring_react.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring_react.spring_react.model.entity.Usuario;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
	
    
    Optional<Usuario> findByEmail(String email);
}
