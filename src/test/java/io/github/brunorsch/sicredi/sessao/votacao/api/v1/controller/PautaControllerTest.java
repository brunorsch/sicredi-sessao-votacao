package io.github.brunorsch.sicredi.sessao.votacao.api.v1.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
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

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CriarPautaRequest.CriarPautaRequestBuilder;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.PautaResponse;
import io.github.brunorsch.sicredi.sessao.votacao.service.CrudPautaService;
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

@WebMvcTest(PautaController.class)
class PautaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CrudPautaService service;

    @Test
    void postDeveRetornarBadRequestQuandoTituloForNulo() throws Exception {
        var request = Random.obj(CriarPautaRequestBuilder.class)
            .titulo(null)
            .build();

        this.mockMvc.perform(post("/v1/pautas")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postDeveRetornarBadRequestQuandoTituloForVazio() throws Exception {
        var request = Random.obj(CriarPautaRequestBuilder.class)
            .titulo("")
            .build();

        this.mockMvc.perform(post("/v1/pautas")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postDeveRetornarBadRequestQuandoTituloEstiverEmBranco() throws Exception {
        var request = Random.obj(CriarPautaRequestBuilder.class)
            .titulo(" ")
            .build();

        this.mockMvc.perform(post("/v1/pautas")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postDeveRetornarBadRequestQuandoTituloForMaiorQue64Caracteres() throws Exception {
        var request = Random.obj(CriarPautaRequestBuilder.class)
            .titulo(randomAlphabetic(65))
            .build();

        this.mockMvc.perform(post("/v1/pautas")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postDeveRetornarBadRequestQuandoDescricaoForVazia() throws Exception {
        var request = Random.obj(CriarPautaRequestBuilder.class)
            .descricao("")
            .build();

        this.mockMvc.perform(post("/v1/pautas")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postDeveRetornarBadRequestQuandoDescricaoEstiverEmBranco() throws Exception {
        var request = Random.obj(CriarPautaRequestBuilder.class)
            .descricao(" ")
            .build();

        this.mockMvc.perform(post("/v1/pautas")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postDeveRetornar201QuandoRequisicaoValidaComDescricao() throws Exception {
        var request = Random.obj(CriarPautaRequestBuilder.class).build();
        var response = Random.obj(PautaResponse.class);

        when(service.criar(request)).thenReturn(response);

        var body = this.mockMvc.perform(post("/v1/pautas")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();

        assertEquals(response, mapper.readValue(body, PautaResponse.class));
    }

    @Test
    void postDeveRetornar201QuandoRequisicaoValidaSemDescricao() throws Exception {
        var request = Random.obj(CriarPautaRequestBuilder.class)
            .descricao(null)
            .build();
        var response = Random.obj(PautaResponse.class);

        when(service.criar(request)).thenReturn(response);

        var body = this.mockMvc.perform(post("/v1/pautas")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();

        assertEquals(response, mapper.readValue(body, PautaResponse.class));
    }
}