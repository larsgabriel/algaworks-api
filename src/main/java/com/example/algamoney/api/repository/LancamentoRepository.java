package com.example.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.lancamento.LancamentoResositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoResositoryQuery{

}
