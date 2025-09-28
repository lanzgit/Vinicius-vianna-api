package br.edu.infnet.vinicius_vianna.repository;

import br.edu.infnet.vinicius_vianna.model.domain.Estrela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstrelaRepository extends JpaRepository<Estrela, Integer> {

}
