package br.com.controlefinanceiro.model.dto;

import br.com.controlefinanceiro.model.Receita;
import br.com.controlefinanceiro.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoReceitaDTO {

    private Long id_lancamento;

    @Size(min = 3, max = 30, message = "The field must contain between 3 and 30 characters")
    @NotBlank(message = "The field cannot be empty or null")
    private String desc_lancamento;

    @NotNull(message = "The field cannot be empty or null")
    private LocalDate dt_venc;

    @NotNull(message = "Field must not be invalid")
    private Status status;

    @NotNull(message = "Field must not be invalid")
    @Positive(message = "The price must be positive")
    private Double vl_lanc;

    @NotNull(message = "Must have at least one expense")
    private Receita receita;
}
