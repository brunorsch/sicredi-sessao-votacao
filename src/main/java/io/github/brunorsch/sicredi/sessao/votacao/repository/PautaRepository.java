package io.github.brunorsch.sicredi.sessao.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.brunorsch.sicredi.sessao.votacao.domain.Pauta;
public interface PautaRepository extends JpaRepository<Pauta, Long> {
}