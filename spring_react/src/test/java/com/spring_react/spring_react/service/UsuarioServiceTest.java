package com.spring_react.spring_react.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ActiveProfiles;

import com.spring_react.spring_react.exceptions.ErroAutenticacao;
import com.spring_react.spring_react.exceptions.RegraNegocioException;
import com.spring_react.spring_react.model.entity.Usuario;
import com.spring_react.spring_react.model.repository.UsuarioRepository;
import com.spring_react.spring_react.service.impl.UsuarioServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioServiceTest {
	
	
	@Autowired
	UsuarioServiceImpl service;
	
	@Autowired
	UsuarioRepository repository;
	
	
	@Test
	public void deveValidarEmail() {

		boolean thrown = false;
		
		repository.deleteAll();
		
		try {
		service.validarEmail("emali");
		} catch(RegraNegocioException e){
			thrown = true;
		}
		
		Assertions.assertFalse(thrown);
		
				
	}
	
	@Test
	public void deveInvalidarEmail() {
		boolean thrown = false;
		String mensagem = null;
		
		Usuario usuario = new Usuario("usuario", "zue", "senha");
		this.repository.save(usuario);
		
		
		try {
		service.validarEmail("zue");
		} catch(RegraNegocioException e){
			thrown = true;
			mensagem = e.getMessage();
		}
		
		Assertions.assertTrue(thrown);
		Assertions.assertEquals(mensagem, "Email já existe");
				
	}
	
	@Test
	public void devePersistirUsuarioNaBD() {
		Usuario usuario = new Usuario("Luan", "luan@email.com", "senha");
		
		Usuario usuarioSalvo = this.service.salvarUsuario(usuario);
		
		Assertions.assertNotNull(usuarioSalvo.getId());
	}
	
	@Test
	public void deveAutenticarUsuarioComSucesso() {
		repository.deleteAll();

		String email = "luan@email.com";
		String senha = "1235";
		Usuario usuario = new Usuario("Luan", email, senha);
		
		this.service.salvarUsuario(usuario);
		
		boolean execacao = false;
		
		try {
			this.service.autenticar(email, senha);
		}
		catch(ErroAutenticacao e) {
			execacao = true;
		}
		
		Assertions.assertEquals(execacao, false);

	}
	
	@Test
	public void deveNegarAutenticarUsuarioEmail() {
		repository.deleteAll();

		String email = "luani@email.com";
		String senha = "12365";
		

		boolean execacao = false;
		String mensagem = "";
		
		try {
			this.service.autenticar(email, senha);
		}
		catch(ErroAutenticacao e) {
			execacao = true;
			mensagem = e.getMessage();
			
		}
		
		Assertions.assertEquals(execacao, true);
		Assertions.assertEquals(mensagem, "Usuario não encontrado para o email informado.");


	}
	
	@Test
	public void deveNegarAutenticarUsuarioSenha() {
		repository.deleteAll();

		String email = "luani@email.com";
		String senha = "12365";
		
		
		boolean execacao = false;
		String mensagem = "";
		
		Usuario usuario = new Usuario("Luan", email, senha);

		this.service.salvarUsuario(usuario);

		
		try {
			this.service.autenticar(email, "5321");
		}
		catch(ErroAutenticacao e) {
			execacao = true;
			mensagem = e.getMessage();
			
		}
		
		Assertions.assertEquals(execacao, true);
		Assertions.assertEquals(mensagem, "Senha invalida.");


	}
	
	
	

}
