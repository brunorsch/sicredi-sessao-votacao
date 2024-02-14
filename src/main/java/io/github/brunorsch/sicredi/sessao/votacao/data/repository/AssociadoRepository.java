package io.github.brunorsch.sicredi.sessao.votacao.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.brunorsch.sicredi.sessao.votacao.domain.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {
    boolean existsByCpf(final String cpf);
}