package br.edu.infnet.vinicius_vianna.service;


import java.util.List;

public interface CrudService<T, ID> {

    List<T> obterTodos();

    T obterPorId(ID id);

    T criar(T entity);

    T atualizar(T entity, ID id);

    void excluir(ID id);
}
