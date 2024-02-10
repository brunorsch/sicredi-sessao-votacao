package io.github.brunorsch.sicredi.sessao.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.brunorsch.sicredi.sessao.votacao.domain.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {
}