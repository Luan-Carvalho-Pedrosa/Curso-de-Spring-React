package com.spring_react.spring_react.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring_react.spring_react.model.entity.Usuario;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
    Boolean existisByEmail(String email);
}
