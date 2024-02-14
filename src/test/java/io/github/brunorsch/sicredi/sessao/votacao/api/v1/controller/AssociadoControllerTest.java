package io.github.brunorsch.sicredi.sessao.votacao.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CadastrarAssociadoRequest.CadastrarAssociadoRequestBuilder;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.AssociadoResponse;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.AssociadoResponse.AssociadoResponseBuilder;
import io.github.brunorsch.sicredi.sessao.votacao.service.CrudAssociadoService;
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

@WebMvcTest(AssociadoController.class)
class AssociadoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CrudAssociadoService crudService;

    @Test
    void postDeveRetornarBadRequestQuandoCpfNaoInformado() throws Exception {
        this.mockMvc.perform(post("/v1/associados"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postDeveRetornarBadRequestQuandoCpfForNulo() throws Exception {
        final var request = Random.obj(CadastrarAssociadoRequestBuilder.class)
            .cpf(null)
            .build();

        this.mockMvc.perform(post("/v1/associados")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postDeveRetornarBadRequestQuandoCpfForVazio() throws Exception {
        final var request = Random.obj(CadastrarAssociadoRequestBuilder.class)
            .cpf("")
            .build();

        this.mockMvc.perform(post("/v1/associados")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postDeveRetornarBadRequestQuandoCpfEmBranco() throws Exception {
        final var request = Random.obj(CadastrarAssociadoRequestBuilder.class)
            .cpf(" ")
            .build();

        this.mockMvc.perform(post("/v1/associados")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postDeveRetornarBadRequestQuandoCpfInvalido() throws Exception {
        final var request = Random.obj(CadastrarAssociadoRequestBuilder.class)
            .cpf("11111111111")
            .build();

        this.mockMvc.perform(post("/v1/associados")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postDeveRetornarCreatedCorretamente() throws Exception {
        final var request = Random.obj(CadastrarAssociadoRequestBuilder.class)
            .cpf(Random.cpf())
            .build();
        final var response = Random.obj(AssociadoResponseBuilder.class)
            .id(1L)
            .cpf(request.getCpf())
            .build();

        when(crudService.cadastrar(request))
            .thenReturn(response);

        final String responseString = this.mockMvc.perform(post("/v1/associados")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();

        final AssociadoResponse responseRetornada = mapper.readValue(responseString, AssociadoResponse.class);

        assertEquals(response, responseRetornada);
    }
}