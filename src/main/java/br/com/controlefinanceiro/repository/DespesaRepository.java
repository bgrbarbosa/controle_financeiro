package br.com.controlefinanceiro.repository;

import br.com.controlefinanceiro.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}
