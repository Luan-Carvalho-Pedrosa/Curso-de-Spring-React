package com.spring_react.spring_react.model.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring_react.spring_react.model.entity.Usuario;
import com.spring_react.spring_react.model.repository.UsuarioRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		Usuario usuario = new Usuario("usuario", "zuario@email.com", "senha");
		this.repository.save(usuario);
		
		Optional<Usuario> achado = this.repository.findByEmail("zuario@email.com");
		
		Assertions.assertEquals(achado.isPresent(), true);
		
		this.repository.delete(usuario);
	}
	
	@Test
	public void deveRetornarFalsoComAInexistenciaDeUmEmail() {
		
		Optional<Usuario> achado = this.repository.findByEmail("tuario@email.com");
		
		Assertions.assertEquals(achado.isPresent(), false);
		
	}
	
	

}
