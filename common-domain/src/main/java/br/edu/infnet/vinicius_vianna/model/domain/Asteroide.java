package br.edu.infnet.vinicius_vianna.model.domain;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Asteroide extends Astro {

    @NotNull(message = "A designação é obrigatória")
    private String designacao;
    
    private String nomeCompleto;
    private String classificacaoOrbital;
    private String periodoOrbital;
    private String tipo;
    private boolean neo;
    private boolean pha;

    @Override
    public String obterTipo() {
        return "Asteroide";
    }
}
