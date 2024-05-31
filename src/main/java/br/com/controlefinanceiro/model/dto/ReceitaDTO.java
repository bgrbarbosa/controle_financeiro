package br.com.controlefinanceiro.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaDTO {

    private Long id_receita;
    private String desc_receita;
}