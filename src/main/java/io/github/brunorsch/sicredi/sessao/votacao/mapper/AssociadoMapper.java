package io.github.brunorsch.sicredi.sessao.votacao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.AssociadoResponse;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Associado;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {
    @Mapping(target = "cpf", expression = "java(io.github.brunorsch.sicredi.sessao.votacao.utils.MascaramentoUtils.mascararCpf(associado.getCpf()))")
    AssociadoResponse toAssociadoResponse(Associado associado);
}