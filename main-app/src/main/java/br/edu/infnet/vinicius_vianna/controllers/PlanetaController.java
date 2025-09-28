package br.edu.infnet.vinicius_vianna.controllers;

import br.edu.infnet.vinicius_vianna.dto.PlanetaRequestDTO;
import br.edu.infnet.vinicius_vianna.dto.PlanetaResponseDTO;
import br.edu.infnet.vinicius_vianna.model.domain.Planeta;
import br.edu.infnet.vinicius_vianna.service.PlanetaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/api")
@Tag(name = "Planetas", description = "Gerenciador de Planetas")
public class PlanetaController {

    private final PlanetaService planetaService;

    public PlanetaController(PlanetaService planetaService) {
        this.planetaService = planetaService;
    }

    @GetMapping("/planeta/{id}")
    public ResponseEntity<PlanetaResponseDTO> obterPorId(@PathVariable Integer id) {

        PlanetaResponseDTO planeta = this.planetaService.obterPorId(id);
        return ResponseEntity.status(OK).body(planeta);
    }

    @GetMapping("/planetas")
    public ResponseEntity<List<PlanetaResponseDTO>> obterPlanetas() {
        List<PlanetaResponseDTO> planetas = this.planetaService.obterTodos();
        if (planetas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(OK).body(planetas);
    }

    @PostMapping("/planeta")
    public ResponseEntity<PlanetaResponseDTO> criarPlaneta(@Valid @RequestBody PlanetaRequestDTO planetaDTO) {
        PlanetaResponseDTO novoPlaneta = this.planetaService.criar(planetaDTO);

        return ResponseEntity.status(CREATED).body(novoPlaneta);
    }

    @PutMapping("/planeta/{id}")
    public ResponseEntity<PlanetaResponseDTO> atualizarPlaneta(@PathVariable Integer id, @RequestBody PlanetaRequestDTO planetaDTO) {
        PlanetaResponseDTO planetaAtualizado = this.planetaService.atualizar(planetaDTO, id);

        return ResponseEntity.status(OK).body(planetaAtualizado);
    }

    @DeleteMapping("/planeta/{id}")
    public ResponseEntity<Void> excluirPlaneta(@PathVariable Integer id) {

        this.planetaService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/planeta/{id}/nuke")
    public ResponseEntity<PlanetaResponseDTO> destruir(@PathVariable Integer id) {
        PlanetaResponseDTO planetaASerDestruido = this.planetaService.nuke(id);

        return ResponseEntity.status(OK).body(planetaASerDestruido);
    }
}
