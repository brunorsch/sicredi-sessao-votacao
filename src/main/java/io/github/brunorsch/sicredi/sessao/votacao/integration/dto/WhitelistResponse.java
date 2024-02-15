package io.github.brunorsch.sicredi.sessao.votacao.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WhitelistResponse {
    private Status status;

    public enum Status {
        ABLE_TO_VOTE, UNABLE_TO_VOTE
    }
}