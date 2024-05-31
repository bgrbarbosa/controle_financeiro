package br.com.controlefinanceiro.model.dto;

import br.com.controlefinanceiro.model.Receita;
import br.com.controlefinanceiro.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoReceitaDTO {

    private Long id_lancamento;
    private String desc_lancamento;
    private LocalDateTime dt_venc;
    private Status status;
    private Double vl_lanc;
    private Receita receita;
}
