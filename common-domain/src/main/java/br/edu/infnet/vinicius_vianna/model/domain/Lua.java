package br.edu.infnet.vinicius_vianna.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Lua extends Astro {

    @NotNull(message = "A distância orbital é obrigatória")
    private Long distanciaOrbitral;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "planeta_id", nullable = false)
    @Valid
    @JsonBackReference
    private Planeta planeta;

    @Override
    public String obterTipo() {
        return "Lua";
    }
}
