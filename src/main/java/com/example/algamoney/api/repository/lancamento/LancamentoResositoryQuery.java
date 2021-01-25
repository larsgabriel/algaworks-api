package com.example.algamoney.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.ProjectionLancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;

public interface LancamentoResositoryQuery {
	
	public Page<Lancamento> filtrar (LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ProjectionLancamento> projectionLancamento (LancamentoFilter lancamentoFilter, Pageable pageable);
	
}
