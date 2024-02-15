package io.github.brunorsch.sicredi.sessao.votacao.integration;

import static io.github.brunorsch.sicredi.sessao.votacao.integration.dto.WhitelistResponse.Status.ABLE_TO_VOTE;
import static io.github.brunorsch.sicredi.sessao.votacao.integration.dto.WhitelistResponse.Status.UNABLE_TO_VOTE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.brunorsch.sicredi.sessao.votacao.integration.dto.WhitelistResponse;

@RestClientTest(WhitelistIntegration.class)
class WhitelistIntegrationTest {
    private static final String CPF = "12345678909";

    @Autowired
    private WhitelistIntegration integration;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(integration, "url", "whitelist/{cpf}");
    }

    @Test
    void isCpfAptoDeveTrueQuandoStatusIgualAbleToVote() throws Exception {
        mockRequest(ABLE_TO_VOTE);

        final var resultado = integration.isCpfApto(CPF);

        assertTrue(resultado);
    }

    @Test
    void isCpfAptoDeveFalseQuandoStatusUnableToVote() throws Exception {
        mockRequest(UNABLE_TO_VOTE);

        final var resultado = integration.isCpfApto(CPF);

        assertFalse(resultado);
    }

    private void mockRequest(final WhitelistResponse.Status status) throws Exception {
        server.reset();

        final var body = objectMapper.writeValueAsString(new WhitelistResponse(status));

        server.expect(requestTo("whitelist/" + CPF))
            .andRespond(withSuccess(body, APPLICATION_JSON));
    }
}