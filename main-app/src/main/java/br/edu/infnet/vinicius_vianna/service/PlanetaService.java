package br.edu.infnet.vinicius_vianna.service;

import br.edu.infnet.vinicius_vianna.dto.PlanetaRequestDTO;
import br.edu.infnet.vinicius_vianna.dto.PlanetaResponseDTO;
import br.edu.infnet.vinicius_vianna.model.domain.Lua;
import br.edu.infnet.vinicius_vianna.model.domain.Planeta;
import br.edu.infnet.vinicius_vianna.model.domain.exceptions.PlanetaNaoEncotradoException;
import br.edu.infnet.vinicius_vianna.repository.PlanetaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanetaService {

    private final PlanetaRepository planetaRepository;
    private final ValidacaoService validacaoService;

    public Planeta obterPlaneta(Integer id) {
        return this.planetaRepository.findById(id)
            .orElseThrow(() -> new PlanetaNaoEncotradoException("Planeta não encontrado"));
    }

    @Transactional
    public PlanetaResponseDTO criar(PlanetaRequestDTO planetaDTO) {
        this.validacaoService.validarAstro(planetaDTO, planetaDTO.getNome());
        if (planetaDTO.getId() != null && planetaDTO.getId() != 0) {
            throw new IllegalArgumentException("Um novo planeta não pode ter um ID na inclusão");
        }

        Planeta planeta = new Planeta();
        planeta.setNome(planetaDTO.getNome());
        planeta.setDescricao(planetaDTO.getDescricao());
        planeta.setTemperaturaMedia(planetaDTO.getTemperaturaMedia());
        planeta.setEhHabitavel(planetaDTO.isEhHabitavel());
        planeta.setGravidade(planetaDTO.getGravidade());
        planeta.setTemSateliteNatural(planetaDTO.isTemSateliteNatural());

        if (planetaDTO.getLuas() != null && !planetaDTO.getLuas().isEmpty()) {
            planetaDTO.getLuas().forEach(lua -> lua.setPlaneta(planeta));
        }

        this.planetaRepository.save(planeta);
        return PlanetaResponseDTO.from(planeta);
    }

    @Transactional
    public PlanetaResponseDTO obterPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("o id não pode ser nulo ou zero");
        }
        Planeta planeta = this.planetaRepository
            .findById(id)
            .orElseThrow(() -> new PlanetaNaoEncotradoException(
                "O planeta não foi encontrado"
            ));

        return PlanetaResponseDTO.from(planeta);
    }

    @Transactional
    public void excluir(Integer id) {
        Planeta planeta = this.planetaRepository
            .findById(id)
            .orElseThrow(() -> new PlanetaNaoEncotradoException(
                "O planeta não foi encontrado"
            ));
        this.planetaRepository.delete(planeta);
    }

    public List<PlanetaResponseDTO> obterTodos() {

        return this.planetaRepository.findAllWithLuas().stream()
            .map(PlanetaResponseDTO::from)
            .toList();
    }

    @Transactional
    public PlanetaResponseDTO atualizar(PlanetaRequestDTO planetaDTO, Integer id) {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("O ID não pode ser zero ou nulo");
        }
        this.validacaoService.validarAstro(planetaDTO, planetaDTO.getNome());

        Planeta planetaExistente = this.planetaRepository
            .findById(id)
            .orElseThrow(() -> new PlanetaNaoEncotradoException(
                "O planeta não foi encontrado"
            ));
        if (id == null || id == 0) {
            throw new IllegalArgumentException("O ID não pode ser zero ou nulo");
        }
        this.validacaoService.validarAstro(planetaDTO, planetaDTO.getNome());

        planetaExistente.setNome(planetaDTO.getNome());
        planetaExistente.setDescricao(planetaDTO.getDescricao());
        planetaExistente.setTemperaturaMedia(planetaDTO.getTemperaturaMedia());
        planetaExistente.setEhHabitavel(planetaDTO.isEhHabitavel());
        planetaExistente.setGravidade(planetaDTO.getGravidade());
        planetaExistente.setTemSateliteNatural(planetaDTO.isTemSateliteNatural());

        this.planetaRepository.save(planetaExistente);
        return PlanetaResponseDTO.from(planetaExistente);
    }

    @Transactional
    public PlanetaResponseDTO nuke(Integer id) {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("O ID não pode ser zero ou nulo");
        }
        Planeta planeta = this.planetaRepository.findById(id)
            .orElseThrow(() -> new PlanetaNaoEncotradoException(
                "O planeta não foi encontrado"
            ));
        if (!planeta.isEhHabitavel()) {
            System.out.println("O planeta " + planeta.getNome() + "já não é mais habitável");
            return PlanetaResponseDTO.from(planeta);
        }
        planeta.setEhHabitavel(false);

        return PlanetaResponseDTO.from(this.planetaRepository.save(planeta));
    }
}
