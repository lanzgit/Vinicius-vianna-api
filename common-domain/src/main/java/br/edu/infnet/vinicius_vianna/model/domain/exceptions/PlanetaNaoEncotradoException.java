package br.edu.infnet.vinicius_vianna.model.domain.exceptions;

public class PlanetaNaoEncotradoException extends RuntimeException {

    public PlanetaNaoEncotradoException(String mensagem) {
        super(mensagem);
    }
}
