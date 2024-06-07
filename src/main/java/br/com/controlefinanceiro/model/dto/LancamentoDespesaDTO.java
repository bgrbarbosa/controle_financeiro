package br.com.controlefinanceiro.model.dto;

import br.com.controlefinanceiro.model.Despesa;
import br.com.controlefinanceiro.model.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoDespesaDTO {

    private Long id_lancamento;

    @Size(min = 3, max = 30, message = "The field must contain between 3 and 30 characters")
    @NotBlank(message = "Field must not be invalid")
    private String desc_lancamento;

    @NotNull(message = "The field cannot be empty or null")
    private LocalDate dt_venc;

    @NotNull(message = "Field must not be invalid")
    private Status status;

    @NotNull(message = "Field must not be invalid")
    @Positive(message = "The price must be positive")
    private Double vl_lanc;

    @NotNull(message = "Must have at least one expense")
    private Despesa despesa;
}
