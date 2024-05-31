package br.com.controlefinanceiro.repository;

import br.com.controlefinanceiro.model.Despesa;
import br.com.controlefinanceiro.model.LancamentoReceita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoReceitaRepository extends JpaRepository<LancamentoReceita, Long> {
}
