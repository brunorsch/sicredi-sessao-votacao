package io.github.brunorsch.sicredi.sessao.votacao.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PAUTA")
@Data
public class Pauta {
    @Id
    @SequenceGenerator(name="PAUTA_ID_SEQ", sequenceName="PAUTA_ID_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PAUTA_ID_SEQ")
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "TITULO", nullable = false, length = 64)
    private String titulo;

    @Column(name = "DESCRICAO", length = 2000)
    private String descricao;

    @Column(name = "DT_FIM_VOTACAO")
    private LocalDateTime dataFimVotacao;
}