package com.spring_react.spring_react.service;

import java.util.List;

import com.spring_react.spring_react.model.entity.Lancamento;
import com.spring_react.spring_react.model.entity.StatusLancamento;

public interface LancamentoService {
	
	public Lancamento salvar(Lancamento lancamento);
	
	public Lancamento atualizar(Lancamento lancamento);
	
	public void deletar(Lancamento lancamento);
	
	public List<Lancamento> buscar(Lancamento lancamentoFiltro);
	
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status);
	
	public void validar(Lancamento lancamento);




}
