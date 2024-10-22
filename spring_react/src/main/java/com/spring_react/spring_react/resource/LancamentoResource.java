package com.spring_react.spring_react.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring_react.spring_react.dto.AtualizaStatusDTO;
import com.spring_react.spring_react.dto.LancamentoDTO;
import com.spring_react.spring_react.exceptions.RegraNegocioException;
import com.spring_react.spring_react.model.entity.Lancamento;
import com.spring_react.spring_react.model.entity.StatusLancamento;
import com.spring_react.spring_react.model.entity.TipoLancamento;
import com.spring_react.spring_react.model.entity.Usuario;
import com.spring_react.spring_react.service.LancamentoService;
import com.spring_react.spring_react.service.UsuarioService;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {
	
	private LancamentoService service;
	
	private UsuarioService usuarioService;
	
	public LancamentoResource(LancamentoService service, UsuarioService usuarioService) {
		this.service = service;
		this.usuarioService = usuarioService;
	}
	
	
	@GetMapping
	public ResponseEntity<?> buscar(		
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "mes", required = false) Long mes,
			@RequestParam(value = "ano", required = false) Long ano,
			@RequestParam("usuario") Long  idUsuario
      ) {
		try {
			Lancamento lancamentoFiltro = new Lancamento();
			lancamentoFiltro.setDescricao(descricao);
			lancamentoFiltro.setMes(mes);
			lancamentoFiltro.setAno(ano);
			
			Usuario usuario = this.usuarioService.obterPorId(idUsuario);
			lancamentoFiltro.setUsuario(usuario);
			List<Lancamento> lancamentos = this.service.buscar(lancamentoFiltro);
			
			return ResponseEntity.ok(lancamentos);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body(e.getMessage());

		}
		
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody LancamentoDTO dto){
		try {
			Lancamento entidade = this.converter(dto);
			entidade = this.service.salvar(entidade);
			return ResponseEntity.ok(entidade);
			
		} catch (RegraNegocioException e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody LancamentoDTO dto) {
		try {
			Lancamento entity = this.service.obterPorId(id).get();
			Lancamento lancamentoAtualizado = this.converter(dto);
			lancamentoAtualizado.setId(entity.getId());
			this.service.atualizar(lancamentoAtualizado);
			return ResponseEntity.ok(lancamentoAtualizado);
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}
	
	@PutMapping("{id}/atualiza_status")
	public ResponseEntity<?> atualizarStatus(@PathVariable Long id,@RequestBody AtualizaStatusDTO dto) {

		StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
		if(statusSelecionado == null) {
			return  ResponseEntity.badRequest().body("Envie um status valido");
		}
		
		try {
			Lancamento entity = this.service.obterPorId(id).get();
			entity.setStatus(statusSelecionado);
			this.service.atualizar(entity);
			return ResponseEntity.ok(entity);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());

			// TODO: handle exception
		}
				}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		try {
			Lancamento entity = this.service.obterPorId(id).get();
			this.service.deletar(entity);
			return ResponseEntity.ok(entity);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Lancamento não encontrado na base de dados");

		}
	}
	
	private Lancamento converter(LancamentoDTO dto) {
		Lancamento lancamento = new Lancamento();
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setAno(dto.getAno());
		lancamento.setMes(dto.getMes());
		lancamento.setValor(dto.getValor());
		lancamento.setUsuario(this.usuarioService.obterPorId(dto.getIdUsuario()));
		lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()) );
		lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		lancamento.setDataCadastro(null);

		return lancamento;
	}
	
	private LancamentoDTO converter(Lancamento lancamento) {
		return LancamentoDTO.builder()
				.id(lancamento.getId())
				.descricao(lancamento.getDescricao())
				.valor(lancamento.getValor())
				.mes(lancamento.getMes())
				.ano(lancamento.getAno())
				.status(lancamento.getStatus().name())
				.tipo(lancamento.getTipo().name())
				.idUsuario(lancamento.getUsuario().getId())
				.build();
				
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obterLancamento(@PathVariable("id") Long id) {
		
		try {
			Lancamento lancamento = this.service.obterPorId(id).get();
			return ResponseEntity.ok(this.converter(lancamento));
		} catch (Exception e) {
			// TODO: handle exception
			return  ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
			}
	
	

}
