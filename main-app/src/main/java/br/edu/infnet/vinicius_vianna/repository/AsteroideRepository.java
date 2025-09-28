package br.edu.infnet.vinicius_vianna.repository;

import br.edu.infnet.vinicius_vianna.model.domain.Asteroide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsteroideRepository extends JpaRepository<Asteroide, Integer> {
    
}
