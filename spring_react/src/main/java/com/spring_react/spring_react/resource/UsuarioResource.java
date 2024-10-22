package com.spring_react.spring_react.resource;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_react.spring_react.dto.UsuarioDTO;
import com.spring_react.spring_react.exceptions.ErroAutenticacao;
import com.spring_react.spring_react.exceptions.RegraNegocioException;
import com.spring_react.spring_react.model.entity.Usuario;
import com.spring_react.spring_react.service.LancamentoService;
import com.spring_react.spring_react.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody UsuarioDTO dto) {
		Usuario usuairo = new Usuario(dto.getNome(), dto.getEmail(), dto.getSenha());
		try {
			Usuario usuarioSalvo = this.service.salvarUsuario(usuairo);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
			
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity<?> autenticar(@RequestBody UsuarioDTO dto) {
		try {
			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		} catch (ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}

	@GetMapping
	public ResponseEntity<?> listarUsuarios(){
		return new ResponseEntity(this.service.listarUsuarios(), HttpStatus.FOUND);

	}
	
	@GetMapping("{id}/saldo")
	public ResponseEntity<?> obtersaldo(@PathVariable("id") Long id){
		try {
			Usuario usuario = this.service.obterPorId(id);
			BigDecimal saldo = this.lancamentoService.obterSaldoPorUsuario(id);
			
			return ResponseEntity.ok(saldo);

			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}

	}
	
	
	

}
