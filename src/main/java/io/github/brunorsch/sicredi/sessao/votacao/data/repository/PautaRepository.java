package io.github.brunorsch.sicredi.sessao.votacao.data.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import io.github.brunorsch.sicredi.sessao.votacao.domain.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
    @Query("SELECT COUNT(1) > 0 FROM Pauta p WHERE p.id = :id AND p.dataHoraFimVotacao >= CURRENT_TIMESTAMP")
    boolean isSessaoEmAndamentoPorId(final Long id);

    @Query("SELECT p.votacaoApurada FROM Pauta p WHERE p.id = :id")
    boolean isVotacaoApuradaPorId(final Long id);

    @Modifying
    @Query("UPDATE Pauta p SET p.votacaoApurada = :votacaoApurada WHERE p.id = :id")
    void setVotacaoApuradaPorId(final Long id, final boolean votacaoApurada);

    @Query("SELECT p FROM Pauta p WHERE CURRENT_TIMESTAMP > p.dataHoraFimVotacao AND p.votacaoApurada = false")
    List<Pauta> buscarTodasComSessaoFinalizadaEVotacaoNaoApurada();
}