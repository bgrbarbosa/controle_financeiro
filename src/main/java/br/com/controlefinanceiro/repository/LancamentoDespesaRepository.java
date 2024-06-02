package br.com.controlefinanceiro.repository;

import br.com.controlefinanceiro.model.LancamentoDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoDespesaRepository extends JpaRepository<LancamentoDespesa, Long> {
    @Query(value = "SELECT * FROM TB_LANC_DESPESA ld WHERE ld.dt_venc = :dt_venc", nativeQuery = true)
    List<LancamentoDespesa> findByDate(LocalDate dt_venc);

    @Query(value = "SELECT * FROM TB_LANC_DESPESA ld WHERE ld.dt_venc BETWEEN :dt_init AND :dt_final", nativeQuery = true)
    List<LancamentoDespesa> findByPeriod(LocalDate dt_init, LocalDate dt_final);
    @Query(value = "SELECT * FROM TB_LANC_DESPESA ld WHERE ld.dt_venc BETWEEN :dt_init AND :dt_final AND ld.status = :status", nativeQuery = true)
    List<LancamentoDespesa> findByStatus(LocalDate dt_init, LocalDate dt_final, String status);


}
