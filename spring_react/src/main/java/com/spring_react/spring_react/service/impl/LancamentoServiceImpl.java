package com.spring_react.spring_react.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring_react.spring_react.exceptions.RegraNegocioException;
import com.spring_react.spring_react.model.entity.Lancamento;
import com.spring_react.spring_react.model.entity.StatusLancamento;
import com.spring_react.spring_react.model.repository.LancamentoRepository;
import com.spring_react.spring_react.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {
	
	@Autowired
	private LancamentoRepository repository;

	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		this.validar(lancamento);
		lancamento.setStatus(StatusLancamento.PENDENTE);
		return this.repository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		Objects.requireNonNull(lancamento.getId());
		this.validar(lancamento);
		return this.repository.save(lancamento);
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		Objects.requireNonNull(lancamento.getId());
		repository.delete(lancamento);
		
	}

	@Override
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		// TODO Auto-generated method stub
		Example example = Example.of(lancamentoFiltro,
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		
		return this.repository.findAll(example);
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		// TODO Auto-generated method stub
		lancamento.setStatus(status);
		this.atualizar(lancamento);
		
	}

	@Override
	public void validar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma descricao valida");
		}
		
		if(lancamento.getMes() == null || lancamento.getMes() <1 || lancamento.getMes() > 12) {
			throw new RegraNegocioException("Infomre um Mês válido.");
		}
		
		if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4 ) {
			throw new RegraNegocioException("Infomre um ano válido.");
		}
		
		if(lancamento.getUsuario() ==  null ) {
			throw new RegraNegocioException("Infomre um usuario.");

		}
		
		if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) <1) {
			throw new RegraNegocioException("informe um Valor válido");
		}
		
		if(lancamento.getTipo() == null) {
			throw new RegraNegocioException("informe um Tipo de lançamento");

		}
		
	}

}
