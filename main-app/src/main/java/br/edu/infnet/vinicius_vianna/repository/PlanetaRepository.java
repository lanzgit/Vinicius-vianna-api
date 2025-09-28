package br.edu.infnet.vinicius_vianna.repository;

import br.edu.infnet.vinicius_vianna.model.domain.Planeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanetaRepository extends JpaRepository<Planeta, Integer> {

    @Query("SELECT DISTINCT p FROM Planeta p LEFT JOIN FETCH p.luas")
    List<Planeta> findAllWithLuas();

    @Query("SELECT p FROM Planeta p LEFT JOIN FETCH p.luas WHERE p.id = :id")
    Optional<Planeta> findByIdWithLuas(@Param("id") Integer id);
}
