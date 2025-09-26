package br.edu.infnet.viniciusviannaapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeoSimplificadoDto {
    private String nome;
    private String nomeCompleto;
    private String classificacaoOrbital;
    private String periodoOrbital;
    private String distanciaMinima;
    private String tipo;
    private boolean ehPerigoso;
    private boolean ehNEO;
}