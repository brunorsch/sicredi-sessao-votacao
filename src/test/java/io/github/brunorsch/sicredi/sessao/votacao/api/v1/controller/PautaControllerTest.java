package io.github.brunorsch.sicredi.sessao.votacao.api.v1.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.ErrorMapperAdvice;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.AbrirSessaoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CriarPautaRequest.CriarPautaRequestBuilder;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.VotoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.VotoRequest.VotoRequestBuilder;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.PautaResponse;
import io.github.brunorsch.sicredi.sessao.votacao.service.CrudPautaService;
import io.github.brunorsch.sicredi.sessao.votacao.service.I18nService;
import io.github.brunorsch.sicredi.sessao.votacao.service.SessaoVotacaoService;
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

@WebMvcTest(PautaController.class)
@Import({ErrorMapperAdvice.class, I18nService.class})
class PautaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CrudPautaService crudService;

    @MockBean
    private SessaoVotacaoService sessaoVotacaoService;

    @Test
    void postDeveRetornarBadRequestQuandoBodyVazio() throws Exception {
        this.mockMvc.perform(post("/v1/pautas"))
            .andExpect(status().isBadRequest());
    }

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
    void postDeveRetornarCreatedQuandoRequisicaoValidaComDescricao() throws Exception {
        var request = Random.obj(CriarPautaRequestBuilder.class).build();
        var response = Random.obj(PautaResponse.class);

        when(crudService.criar(request)).thenReturn(response);

        var body = this.mockMvc.perform(post("/v1/pautas")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();

        assertEquals(response, mapper.readValue(body, PautaResponse.class));
    }

    @Test
    void postDeveRetornarCreatedQuandoRequisicaoValidaSemDescricao() throws Exception {
        var request = Random.obj(CriarPautaRequestBuilder.class)
            .descricao(null)
            .build();
        var response = Random.obj(PautaResponse.class);

        when(crudService.criar(request)).thenReturn(response);

        var body = this.mockMvc.perform(post("/v1/pautas")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();

        assertEquals(response, mapper.readValue(body, PautaResponse.class));
    }

    @Test
    void postVotacaoDeveRetornarBadRequestQuandoBodyVazio() throws Exception {
        this.mockMvc.perform(post("/v1/pautas/{id}/sessao-votacao", nextLong()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postVotacaoDeveRetornarCreatedQuandoRequestValida() throws Exception {
        var idPauta = nextLong();
        var request = Random.obj(AbrirSessaoRequest.class);

        this.mockMvc.perform(post("/v1/pautas/{id}/sessao-votacao", idPauta)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isCreated());

        verify(sessaoVotacaoService).abrir(idPauta, request.getDataHoraFimVotacao());
    }

    @Test
    void postVotoDeveRetornarBadRequestQuandoBodyVazio() throws Exception {
        this.mockMvc.perform(post("/v1/pautas/{id}/voto", nextLong()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postVotoDeveRetornarBadRequestQuandoIdAssociadoForNulo() throws Exception {
        var request = Random.obj(VotoRequestBuilder.class)
            .idAssociado(null)
            .build();

        this.mockMvc.perform(post("/v1/pautas/{id}/voto", nextLong())
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postVotoDeveRetornarBadRequestQuandoOpcaoForNula() throws Exception {
        var request = Random.obj(VotoRequestBuilder.class)
            .opcao(null)
            .build();

        this.mockMvc.perform(post("/v1/pautas/{id}/voto", nextLong())
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void postVotoDeveRetornarCreatedQuandoRequestValida() throws Exception {
        var idPauta = nextLong();
        var request = Random.obj(VotoRequest.class);

        this.mockMvc.perform(post("/v1/pautas/{id}/voto", idPauta)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isCreated());

        verify(sessaoVotacaoService).registrarVoto(idPauta, request);
    }

    @Test
    void getDeveRetornarOkQuandoRequestValida() throws Exception {
        var page = new PageImpl<>(List.of(Random.obj(PautaResponse.class), Random.obj(PautaResponse.class)));

        when(crudService.listar(any()))
            .thenReturn(page);

        this.mockMvc.perform(get("/v1/pautas"))
            .andExpect(status().isOk());
    }
}