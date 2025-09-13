package br.edu.infnet.viniciusviannaapi.model.service;

import java.time.Month;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.viniciusviannaapi.model.domain.Constelacao;

@Service
public class ConstelacaoService {

    public List<Constelacao> obterConstelacoesPorMes(Month mes) {
        throw new UnsupportedOperationException("Método não implementado");
    }

    public List<Constelacao> obterConstelacoesVisiveisAHumanos() {
        throw new UnsupportedOperationException("Método não implementado");
    }

    public List<Constelacao> obterConstelacoesDoZodiaco() {
        throw new UnsupportedOperationException("Método não implementado");
    }
}
