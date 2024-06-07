package br.com.controlefinanceiro.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaDTO {

    private Long id_receita;

    @Size(min = 3, max = 30, message = "The field must contain between 3 and 30 characters")
    @NotBlank(message = "Field must not be invalid")
    private String desc_receita;
}