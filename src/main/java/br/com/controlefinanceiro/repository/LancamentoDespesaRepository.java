package br.com.controlefinanceiro.repository;

import br.com.controlefinanceiro.model.Despesa;
import br.com.controlefinanceiro.model.LancamentoDespesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoDespesaRepository extends JpaRepository<LancamentoDespesa, Long> {
}
