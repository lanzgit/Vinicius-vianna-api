package br.edu.infnet.vinicius_vianna.dto;

import br.edu.infnet.vinicius_vianna.model.CorpoCeleste;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorpoCelesteDTO {
    private String nome;
    private String nomeCompleto;
    private String classificacaoOrbital;
    private String periodoOrbital;
    private String distanciaMinima;
    private String tipo;
    private boolean ehPerigoso;
    private boolean ehNEO;

    public static CorpoCelesteDTO fromCorpoCeleste(CorpoCeleste corpoCeleste) {
        if (corpoCeleste == null) {
            return null;
        }

        CorpoCelesteDTO dto = new CorpoCelesteDTO();
        dto.setNome(corpoCeleste.getNome());
        dto.setNomeCompleto(corpoCeleste.getNomeCompleto());
        dto.setClassificacaoOrbital(corpoCeleste.getClassificacaoOrbital());
        dto.setPeriodoOrbital(formatarPeriodoOrbital(corpoCeleste.getPeriodoOrbital()));
        dto.setDistanciaMinima(formatarDistanciaMinima(corpoCeleste.getDistanciaMinima()));
        dto.setTipo(corpoCeleste.getTipo());
        dto.setEhPerigoso(corpoCeleste.ehPHA());
        dto.setEhNEO(corpoCeleste.ehNEO());

        return dto;
    }

    public static CorpoCelesteDTO of(CorpoCeleste corpoCeleste) {
        return fromCorpoCeleste(corpoCeleste);
    }

    private static String formatarPeriodoOrbital(String periodoOrbital) {
        if (periodoOrbital == null || periodoOrbital.trim().isEmpty()) {
            return periodoOrbital;
        }
        
        if (periodoOrbital.toLowerCase().contains("dias") || 
            periodoOrbital.toLowerCase().contains("days")) {
            return periodoOrbital;
        }
        
        try {
            Double.parseDouble(periodoOrbital.trim());
            return periodoOrbital + " dias";
        } catch (NumberFormatException e) {
            // Se não for um número, retorna como está
            return periodoOrbital;
        }
    }

    private static String formatarDistanciaMinima(String distanciaMinima) {
        if (distanciaMinima == null || distanciaMinima.trim().isEmpty()) {
            return distanciaMinima;
        }
        
        if (distanciaMinima.toLowerCase().contains("au") || 
            distanciaMinima.toLowerCase().contains("ua")) {
            return distanciaMinima;
        }
        
        try {
            Double.parseDouble(distanciaMinima.trim());
            return distanciaMinima + " AU";
        } catch (NumberFormatException e) {
            // Se não for um número, retorna como está
            return distanciaMinima;
        }
    }
}