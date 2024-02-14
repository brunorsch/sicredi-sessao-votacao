package io.github.brunorsch.sicredi.sessao.votacao.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import io.github.brunorsch.sicredi.sessao.votacao.domain.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
    @Query("SELECT COUNT(1) > 0 FROM Pauta p WHERE p.id = :id AND p.dataHoraFimVotacao >= CURRENT_TIMESTAMP")
    boolean isSessaoEmAndamentoById(final Long id);

    @Query("SELECT p.votacaoApurada FROM Pauta p WHERE p.id = :id")
    boolean isVotacaoApuradaById(final Long id);

    @Modifying
    @Query("UPDATE Pauta p SET p.votacaoApurada = :votacaoApurada WHERE p.id = :id")
    void setVotacaoApuradaById(final Long id, final boolean votacaoApurada);
}