package br.com.controlefinanceiro.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaDTO {

    private Long id_despesa;

    @Size(min = 3, max = 30, message = "The field must contain between 3 and 30 characters")
    @NotBlank(message = "The field cannot be empty or null")
    private String desc_despesa;
}
