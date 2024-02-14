package io.github.brunorsch.sicredi.sessao.votacao.data.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.brunorsch.sicredi.sessao.votacao.data.projection.ApuracaoProjection;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByAssociadoIdAndPautaId(final Long idAssociado, final Long idPauta);

    @Query(value = """
        SELECT
            v.opcao as opcao, count(v.opcao) as total
        FROM
            Voto v
        WHERE
            v.pauta.id = :idPauta
        GROUP BY v.opcao
    """)
    Collection<ApuracaoProjection> contarVotosPorIdPauta(final Long idPauta);
}