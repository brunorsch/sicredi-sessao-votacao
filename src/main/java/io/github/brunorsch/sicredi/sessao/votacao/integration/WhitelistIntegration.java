package io.github.brunorsch.sicredi.sessao.votacao.integration;

import static io.github.brunorsch.sicredi.sessao.votacao.integration.dto.WhitelistResponse.Status.ABLE_TO_VOTE;
import static java.util.Objects.nonNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import io.github.brunorsch.sicredi.sessao.votacao.integration.dto.WhitelistResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WhitelistIntegration {
    private final RestClient.Builder restClient;

    @Value("${app.whitelist.url}/{cpf}")
    private String url;

    public boolean isCpfApto(final String cpf) {
        final WhitelistResponse response = restClient.build()
            .get()
            .uri(url, cpf)
            .retrieve()
            .body(WhitelistResponse.class);

        return nonNull(response) && ABLE_TO_VOTE.equals(response.getStatus());
    }
}