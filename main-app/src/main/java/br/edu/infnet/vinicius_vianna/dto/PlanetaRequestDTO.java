package br.edu.infnet.vinicius_vianna.dto;

import java.util.List;

import br.edu.infnet.vinicius_vianna.model.domain.Lua;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanetaRequestDTO  {

    private Integer id;

    @Size(min = 2, max = 15)
    private String nome;

    private double temperaturaMedia;

    @NotBlank(message = "a descrição é obrigatória")
    @Size(min = 3, max = 100, message = "descrição deve ter no mínimo 3 caracteres e no máximo 100 caracteres.")
    private String descricao;
    
    private boolean ehHabitavel;
    
    private double gravidade;
    private boolean temSateliteNatural;
    private String tipo;
    
    private List<Lua> luas;
    private int quantidadeLuas;
}
