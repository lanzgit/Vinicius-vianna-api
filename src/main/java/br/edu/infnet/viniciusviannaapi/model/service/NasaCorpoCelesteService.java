package br.edu.infnet.viniciusviannaapi.model.service;

import br.edu.infnet.viniciusviannaapi.model.clients.NasaSbdbClient;
import br.edu.infnet.viniciusviannaapi.model.domain.CorpoCeleste;
import br.edu.infnet.viniciusviannaapi.model.dto.NeoSimplificadoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NasaCorpoCelesteService {
    
    private final NasaSbdbClient nasaSbdbClient;
    private List<String> neosConhecidos = Arrays.asList(
        "433",      // Eros
        "99942",    // Apophis 
        "1620",     // Geographos
        "4179",     // Toutatis
        "25143",    // Itokawa
        "101955",   // Bennu
        "162173",   // Ryugu
        "2063",     // Bacchus
        "1685",     // Toro
        "3753"      // Cruithne
    );
    
    public CorpoCeleste buscarCorpoCeleste(String designacao) {
        try {
            log.info("Buscando informações do corpo celeste: {}", designacao);
            CorpoCeleste corpoCeleste = nasaSbdbClient.buscarCorpoCeleste(designacao);
            
            if (corpoCeleste == null || corpoCeleste.getObject() == null) {
                throw new RuntimeException("Nenhum dado encontrado para: " + designacao);
            }
            
            log.info("Dados encontrados para: {} - Nome: {}", designacao, corpoCeleste.getNome());

            return corpoCeleste;
        } catch (Exception e) {
            log.error("Erro ao buscar corpo celeste {}: {}", designacao, e.getMessage(), e);
            throw new RuntimeException("Erro ao consultar API da NASA para: " + designacao + ". Detalhes: " + e.getMessage(), e);
        }
    }

    public CorpoCeleste buscarCorpoCelesteDetalhado(String designacao) {
        try {
            log.info("Buscando informações detalhadas do corpo celeste: {}", designacao);
            CorpoCeleste corpoCeleste = nasaSbdbClient.buscarCorpoCelesteDetalhado(designacao, true);
            
            if (corpoCeleste == null || corpoCeleste.getObject() == null) {
                throw new RuntimeException("Nenhum dado encontrado para: " + designacao);
            }
            
            log.info("Dados detalhados encontrados para: {} - Nome: {}", designacao, corpoCeleste.getNome());

            return corpoCeleste;
        } catch (Exception e) {
            log.error("Erro ao buscar corpo celeste detalhado {}: {}", designacao, e.getMessage(), e);
            throw new RuntimeException("Erro ao consultar API da NASA para: " + designacao + ". Detalhes: " + e.getMessage(), e);
        }
    }
    
    public boolean ehPotencialmentePerigoso(CorpoCeleste corpoCeleste) {
        if (corpoCeleste == null || corpoCeleste.getObject() == null) {
            return false;
        }
        
        boolean isNEO = corpoCeleste.ehNEO();
        
        String moid = corpoCeleste.getDistanciaMinima();
        boolean distanciaPerigosa = false;
        
        if (moid != null && !moid.isEmpty()) {
            try {
                double moidValue = Double.parseDouble(moid);
                distanciaPerigosa = moidValue < 0.05;
            } catch (NumberFormatException e) {
                log.warn("Não foi possível converter MOID para número: {}", moid);
            }
        }
        
        return isNEO && distanciaPerigosa;
    }
    
    public List<NeoSimplificadoDto> buscarNeosSimplificados() {
        List<NeoSimplificadoDto> neosSimplificados = new ArrayList<>();
        
        for (String designacao : this.neosConhecidos) {
            try {
                log.info("Buscando NEO: {}", designacao);
                CorpoCeleste corpoCeleste = nasaSbdbClient.buscarCorpoCeleste(designacao);
                
                if (corpoCeleste != null && corpoCeleste.getObject() != null && corpoCeleste.ehNEO()) {
                    String nome = corpoCeleste.getNome();
                    String nomeCompleto = corpoCeleste.getNomeCompleto();
                    String classificacaoOrbital = corpoCeleste.getClassificacaoOrbital();
                    String periodoOrbital = corpoCeleste.getPeriodoOrbital() + " dias";
                    String distancia = corpoCeleste.getDistanciaMinima() + " AU";
                    String tipo = corpoCeleste.getTipo();
                    boolean ehPerigoso = corpoCeleste.ehPHA();
                    boolean ehNEO = corpoCeleste.ehNEO();
                    
                    if (nome != null && !nome.trim().isEmpty() && 
                        distancia != null && !distancia.trim().isEmpty()) {
                        
                        NeoSimplificadoDto neoDto = new NeoSimplificadoDto(
                            nome, nomeCompleto, classificacaoOrbital, periodoOrbital, distancia, tipo, ehPerigoso, ehNEO
                        );
                        neosSimplificados.add(neoDto);
                        log.info("NEO adicionado: {} - Distância: {}", nome, distancia);
                    }
                }
                Thread.sleep(100);
            } catch (Exception e) {
                log.warn("Erro ao buscar NEO {}: {}", designacao, e.getMessage());
            }
        }
        log.info("Total de NEOs encontrados: {}", neosSimplificados.size());

        return neosSimplificados;
    }
}