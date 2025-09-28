package br.edu.infnet.vinicius_vianna.repository;

import br.edu.infnet.vinicius_vianna.model.domain.Lua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LuaRepository extends JpaRepository<Lua, Integer> {

    Optional<List<Lua>> findByPlanetaId(Integer planetaId);

    List<Lua> findByDistanciaOrbitralGreaterThan(Double distancia);
}
