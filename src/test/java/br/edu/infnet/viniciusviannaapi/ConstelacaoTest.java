package br.edu.infnet.viniciusviannaapi;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.edu.infnet.viniciusviannaapi.model.domain.Constelacao;
import br.edu.infnet.viniciusviannaapi.model.service.ConstelacaoService;

public class ConstelacaoTest {

    private ConstelacaoService constelacaoService;
    private List<Constelacao> constelacoes = new ArrayList<>();
    private Constelacao constelacao1;
    private Constelacao constelacao2;
    private Constelacao constelacao3;

    @BeforeEach
    void setUp() {
        constelacao1 = new Constelacao(
            1L,
            "Cisne",
            "Cyg" ,
            5,
            true,
            Set.of(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER)
        );

        constelacao2 = new Constelacao(
            2L,
            "Andrômeda",
            "And" ,
            7,
            true,
            Set.of(Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER)
        );

        constelacao3 = new Constelacao(
            3L,
            "Virgem",
            "Vir",
            9,
            true,
            Set.of(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)
        );

        constelacaoService = new ConstelacaoService();
    }

    @Test
    @DisplayName("Deve retornar constelações visíveis em um mês específico")
    void deveRetornarConstelacoesVisiveis_EmUmMesEspecifico() {
        //dado
        Month mes = Month.OCTOBER;
        constelacaoService.salvar(constelacao1);
        constelacaoService.salvar(constelacao2);
        constelacaoService.salvar(constelacao3);

        //quando
        List<Constelacao> resultado = constelacaoService.obterConstelacoesPorMes(mes);

        //entao
        assertEquals(2, resultado.size(), "Deveria retornar 2 constelações visíveis em Outubro");
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Cisne")), "Deveria conter a constelação Cisne");
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Andrômeda")), "Deveria conter a constelação Andrômeda");
    }

    @Test
    @DisplayName("Deve retornar apenas constelações do zodíaco")
    void deveRetornarApenasConstelacoesDoZodiaco() {
        //dado
        constelacaoService.salvar(constelacao1);
        constelacaoService.salvar(constelacao2);
        constelacaoService.salvar(constelacao3);

        //quando
        List<Constelacao> resultado = constelacaoService.obterConstelacoesDoZodiaco();

        //entao
        assertEquals(1, resultado.size(), "Deveria retornar 1 constelação do zodíaco");
        assertTrue(resultado.stream().allMatch(Constelacao::ehDoZodiaco));
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Virgem")), "Deveria conter a constelação Virgem");
    }

    @Test
    @DisplayName("deve retornar lista vazia quando não há constelação do zodíaco")
    void deveRetornarListaVazia_quandoNaoHaConstelacaoDoZodiaco() {
        //dado
        constelacoes.clear();

        //quando
        List<Constelacao> resultado = constelacaoService.obterConstelacoesDoZodiaco();

        //entao
        assertTrue(resultado.isEmpty(), "Deveria retornar lista vazia");
    }

    @Test
    @DisplayName("Deve retornar apenas constelações visíveis a humanos")
    void deveRetornarApenasConstelacoes_quandoVisiveisAHumanos() {
        //dado
        constelacaoService.salvar(constelacao1);
        constelacaoService.salvar(constelacao2);
        constelacaoService.salvar(constelacao3);

        //quando
        List<Constelacao> resultado = constelacaoService.obterConstelacoesVisiveisAHumanos();

        //entao
        assertEquals(3, resultado.size(), "Deveria retornar 3 constelações visíveis a humanos");
        assertTrue(resultado.stream().allMatch(Constelacao::getVisivelAHumanos));
    }
}
