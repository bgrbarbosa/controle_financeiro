package br.com.controlefinanceiro.model.dto;

import br.com.controlefinanceiro.model.Despesa;
import br.com.controlefinanceiro.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoDespesaDTO {

    private Long id_lancamento;
    private String desc_lancamento;
    private LocalDateTime dt_venc;
    private Status status;
    private Double vl_lanc;
    private Despesa despesa;
}
