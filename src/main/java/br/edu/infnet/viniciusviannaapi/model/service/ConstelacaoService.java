package br.edu.infnet.viniciusviannaapi.model.service;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.edu.infnet.viniciusviannaapi.model.domain.Constelacao;

@Service
public class ConstelacaoService {

    private List<Constelacao> constelacoes = new java.util.ArrayList<>();

    public void salvar(Constelacao constelacao) {
        constelacoes.add(constelacao);
    }
    
    public List<Constelacao> obterConstelacoesPorMes(Month mes) {
        return constelacoes.stream()
            .filter(constelacao -> constelacao.ehVisivelEm(mes))
            .collect(Collectors.toList());

    }

    public List<Constelacao> obterConstelacoesVisiveisAHumanos() {
        return constelacoes.stream()
            .filter(Constelacao::getVisivelAHumanos)
            .collect(Collectors.toList());
    }

    public List<Constelacao> obterConstelacoesDoZodiaco() {
        return constelacoes.stream()
            .filter(Constelacao::ehDoZodiaco)
            .collect(Collectors.toList());
    }
}
