package br.edu.infnet.viniciusviannaapi.controller;

import br.edu.infnet.viniciusviannaapi.model.domain.CorpoCeleste;
import br.edu.infnet.viniciusviannaapi.model.dto.NeoSimplificadoDto;
import br.edu.infnet.viniciusviannaapi.model.service.NasaCorpoCelesteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nasa")
@RequiredArgsConstructor
public class NasaController {
    
    private final NasaCorpoCelesteService nasaCorpoCelesteService;
    
    @GetMapping("/corpo-celeste/{designacao}")
    public ResponseEntity<CorpoCeleste> buscarCorpoCeleste(@PathVariable String designacao) {
        CorpoCeleste corpoCeleste = nasaCorpoCelesteService.buscarCorpoCeleste(designacao);
        return ResponseEntity.ok(corpoCeleste);
    }
    
    @GetMapping("/corpo-celeste/{designacao}/detalhado")
    public ResponseEntity<CorpoCeleste> buscarCorpoCelesteDetalhado(@PathVariable String designacao) {
        CorpoCeleste corpoCeleste = nasaCorpoCelesteService.buscarCorpoCelesteDetalhado(designacao);
        return ResponseEntity.ok(corpoCeleste);
    }
    
    @GetMapping("/corpo-celeste/{designacao}/perigoso")
    public ResponseEntity<Boolean> verificarSeEhPerigoso(@PathVariable String designacao) {
        CorpoCeleste corpoCeleste = nasaCorpoCelesteService.buscarCorpoCeleste(designacao);
        boolean ehPerigoso = nasaCorpoCelesteService.ehPotencialmentePerigoso(corpoCeleste);
        return ResponseEntity.ok(ehPerigoso);
    }
    
    @GetMapping("/neos")
    public ResponseEntity<List<NeoSimplificadoDto>> buscarNeos() {
        List<NeoSimplificadoDto> neos = nasaCorpoCelesteService.buscarNeosSimplificados();
        return ResponseEntity.ok(neos);
    }
}