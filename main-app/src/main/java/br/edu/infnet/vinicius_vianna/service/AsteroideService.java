package br.edu.infnet.vinicius_vianna.service;

import br.edu.infnet.vinicius_vianna.clients.NasaSbdbClient;
import br.edu.infnet.vinicius_vianna.dto.CorpoCelesteDTO;
import br.edu.infnet.vinicius_vianna.model.domain.Asteroide;
import br.edu.infnet.vinicius_vianna.repository.AsteroideRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsteroideService implements CrudService<Asteroide, Integer> {

    private final NasaSbdbClient nasaClient;
    private final NasaCorpoCelesteService nasaCorpoCelesteService;
    private final AsteroideRepository asteroideRepository;
    private final ValidacaoService validacaoService;


    private void copyFromCorpoCelesteResponse(CorpoCelesteDTO response, Asteroide asteroide) {
        asteroide.setNomeCompleto(response.getNomeCompleto());
        asteroide.setClassificacaoOrbital(response.getClassificacaoOrbital());
        asteroide.setPeriodoOrbital(response.getPeriodoOrbital());
        asteroide.setTipo(response.getTipo());
        asteroide.setNeo(response.isEhNEO());
        asteroide.setPha(response.isEhPerigoso());
    }

    @Override
    public List<Asteroide> obterTodos() {
        return this.asteroideRepository.findAll();
    }

    @Override
    public Asteroide obterPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("o id não pode ser nulo ou zero");
        }
        return this.asteroideRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Asteroide criar(Asteroide asteroide) {
        this.validacaoService.validarAstro(asteroide, asteroide.getNome());
        if (asteroide.getId() != null && asteroide.getId() != 0) {
            throw new IllegalArgumentException("Um novo asteroide não pode ter um ID na inclusão");
        }

        if (nasaClient != null) {
            try {
                CorpoCelesteDTO response =
                    nasaCorpoCelesteService.buscarCorpoCeleste(asteroide.getDesignacao());
                this.copyFromCorpoCelesteResponse(response, asteroide);
            } catch (Exception e) {
                System.out.println("Erro ao obter dados do corpo celeste: " + e.getMessage());
            }
        }

        return this.asteroideRepository.save(asteroide);
    }

    @Override
    @Transactional
    public Asteroide atualizar(Asteroide asteroide, Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("o id não pode ser nulo ou zero");
        }
        this.validacaoService.validarAstro(asteroide, asteroide.getNome());
        Asteroide asteroideExistente = this.obterPorId(id);
        if (asteroideExistente == null) {
            throw new IllegalArgumentException("Asteroide não encontrado");
        }
        asteroideExistente.setNome(asteroide.getNome());
        asteroideExistente.setTemperaturaMedia(asteroide.getTemperaturaMedia());
        asteroideExistente.setDescricao(asteroide.getDescricao());
        asteroideExistente.setEhHabitavel(asteroide.isEhHabitavel());

        if (asteroide.getDesignacao() != asteroideExistente.getDesignacao()) {
            asteroideExistente.setDesignacao(asteroide.getDesignacao());
            CorpoCelesteDTO response =
                nasaCorpoCelesteService.buscarCorpoCeleste(asteroide.getDesignacao());
            this.copyFromCorpoCelesteResponse(response, asteroideExistente);
        }

        return this.asteroideRepository.save(asteroideExistente);
    }

    @Override
    @Transactional
    public void excluir(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("o id não pode ser nulo ou zero");
        }

        Asteroide asteroideExistente = this.asteroideRepository.findById(id).orElse(null);
        if (asteroideExistente == null) {
            throw new IllegalArgumentException("Asteroide não encontrado");
        }

        this.asteroideRepository.delete(asteroideExistente);
    }
}
