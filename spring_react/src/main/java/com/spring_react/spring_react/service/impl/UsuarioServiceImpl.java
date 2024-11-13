package com.spring_react.spring_react.service.impl;

import java.awt.Window;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring_react.spring_react.ThisApplication;
import com.spring_react.spring_react.exceptions.ErroAutenticacao;
import com.spring_react.spring_react.exceptions.RegraNegocioException;
import com.spring_react.spring_react.model.entity.Usuario;
import com.spring_react.spring_react.model.repository.UsuarioRepository;
import com.spring_react.spring_react.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder encoder;
    
    

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.encoder = encoder;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		ThisApplication.setLabel1("autenticando...");

    	Optional<Usuario> usuario = this.usuarioRepository.findByEmail(email);
    	
		if (!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuario não encontrado para o email informado.");
		}

		Usuario user = usuario.get();
		
		boolean senhasBatem  = encoder.matches(senha, user.getSenha());
    	
		if (!senhasBatem) {
			throw new ErroAutenticacao("Senha invalida.");

		}

		ThisApplication.setLabel1("Login valido!");

		
    	return user;
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		this.criptografarSenha(usuario);
    	return this.usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
    	
        boolean existe = this.validarEmailBoolean(email);
        if(existe){
            throw new RegraNegocioException("Email já existe");
        }
        
    }
    
    public boolean validarEmailBoolean(String email) {
    	return this.usuarioRepository.findByEmail(email).isPresent();
    	
    }

	@Override
	public Usuario obterPorId(Long id) {
		// TODO Auto-generated method stub
		return this.usuarioRepository.findById(id).get();
	}

	@Override
	public List<Usuario> listarUsuarios() {
		// TODO Auto-generated method stub
		ThisApplication.setLabel1(("Listando usuarios"));
		return this.usuarioRepository.findAll();
	}

	private void criptografarSenha(Usuario usuario) {
		String senha = usuario.getSenha();
		String senhaCripto = encoder.encode(senha);
		usuario.setSenha(senhaCripto);
	}
    
}
