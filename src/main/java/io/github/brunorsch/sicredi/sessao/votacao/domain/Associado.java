package io.github.brunorsch.sicredi.sessao.votacao.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ASSOCIADO")
@Data
public class Associado {
    @Id
    @SequenceGenerator(name = "VOTO_ID_SEQ", sequenceName = "VOTO_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VOTO_ID_SEQ")
    private Long id;

    @Column(name = "CPF", nullable = false, unique = true, length = 11)
    private String cpf;
}