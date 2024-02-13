package io.github.brunorsch.sicredi.sessao.votacao.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "VOTO")
@Data
public class Voto {
    @Id
    @SequenceGenerator(name = "VOTO_ID_SEQ", sequenceName = "VOTO_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VOTO_ID_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PAUTA", nullable = false)
    private Pauta pauta;

    @ManyToOne
    @JoinColumn(name = "ID_ASSOCIADO", nullable = false)
    private Associado associado;

    @Enumerated(EnumType.STRING)
    @Column(name = "OPCAO", nullable = false)
    private Opcao opcao;
}