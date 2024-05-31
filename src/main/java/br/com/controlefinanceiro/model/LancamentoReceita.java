package br.com.controlefinanceiro.model;

import br.com.controlefinanceiro.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_lanc_receita")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoReceita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_lancamento;

    @Column
    private String desc_lancamento;

    @Column
    private LocalDateTime dt_venc;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private Double vl_lanc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_receita")
    private Receita receita;
}
