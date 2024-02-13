package io.github.brunorsch.sicredi.sessao.votacao.service;

import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.brunorsch.sicredi.sessao.votacao.domain.Associado;
import io.github.brunorsch.sicredi.sessao.votacao.exception.AssociadoNaoEncontradoException;
import io.github.brunorsch.sicredi.sessao.votacao.repository.AssociadoRepository;
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

@ExtendWith(MockitoExtension.class)
class AssociadoServiceTest {
    @InjectMocks
    private AssociadoService service;

    @Mock
    private AssociadoRepository repository;

    @Test
    void deveLancarAssociadoNaoEncontradoExceptionQuandoAssociadoNaoExistir() {
        when(repository.findById(anyLong()))
            .thenReturn(Optional.empty());

        assertThrows(AssociadoNaoEncontradoException.class, () -> service.buscar(nextLong()));
    }

    @Test
    void deveRetornarAssociadoCorretamente() {
        final var associado = Random.obj(Associado.class);
        when(repository.findById(anyLong()))
            .thenReturn(Optional.of(associado));

        final var resultado = service.buscar(nextLong());

        assertEquals(associado, resultado);
    }
}