package br.edu.infnet.viniciusviannaapi.model.service;

import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.edu.infnet.viniciusviannaapi.model.domain.Constelacao;

@Service
public class ConstelacaoService {

    private List<Constelacao> constelacoes = new java.util.ArrayList<>();

    public void salvar(Constelacao constelacao) {
        Objects.requireNonNull(constelacao, "Constelação não pode ser nula");
        constelacoes.add(constelacao);
    }

    public List<Constelacao> obterConstelacoesPorMes(Month mes) {
        Objects.requireNonNull(mes, "Mês não pode ser nulo");
        List<Constelacao> constelacoesFiltradas = filtrarConstelacoes(c -> c.ehVisivelEm(mes));
        return constelacoesFiltradas;

    }

    public List<Constelacao> obterConstelacoesVisiveisAHumanos() {
        List<Constelacao> constelacoesVisiveis = filtrarConstelacoes(Constelacao::getVisivelAHumanos);
        return constelacoesVisiveis;
    }

    public List<Constelacao> obterConstelacoesDoZodiaco() {
        List<Constelacao> constelacoesDoZodiaco = filtrarConstelacoes(Constelacao::ehDoZodiaco);
        return constelacoesDoZodiaco;
    }

    private List<Constelacao> filtrarConstelacoes(Predicate<Constelacao> filtro) {
        return constelacoes.stream()
            .filter(filtro)
            .collect(Collectors.toList());
    }
}
