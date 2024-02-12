package io.github.brunorsch.sicredi.sessao.votacao.mapper;

import org.mapstruct.Mapper;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CriarPautaRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.PautaResponse;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Pauta;

@Mapper(componentModel = "spring")
public interface PautaMapper {
    Pauta fromCriarPautaRequest(CriarPautaRequest request);

    PautaResponse toPautaResponse(Pauta pauta);
}