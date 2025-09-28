package br.edu.infnet.vinicius_vianna.model.domain.exceptions;

public class LuaNaoEncontradaException extends RuntimeException {
    public LuaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
