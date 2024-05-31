package br.com.controlefinanceiro.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaDTO {

    private Long id_despesa;
    private String desc_despesa;
}
