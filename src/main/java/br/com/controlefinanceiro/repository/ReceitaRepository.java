package br.com.controlefinanceiro.repository;

import br.com.controlefinanceiro.model.Despesa;
import br.com.controlefinanceiro.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}
