package br.edu.infnet.vinicius_vianna.dto;

import br.edu.infnet.vinicius_vianna.model.domain.Lua;
import br.edu.infnet.vinicius_vianna.model.domain.Planeta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanetaResponseDTO {

    private Integer id;
    private String nome;
    private Double temperaturaMedia;
    private String descricao;
    private Boolean ehHabitavel;
    private Double gravidade;
    private Boolean temSateliteNatural;

    @JsonManagedReference
    private List<Lua> luas;
    private Integer quantidadeLuas;

    public static PlanetaResponseDTO from(Planeta planeta) {
        return Optional.ofNullable(planeta)
            .map(p -> PlanetaResponseDTO.builder()
            .id(p.getId())
            .nome(p.getNome())
            .temperaturaMedia(p.getTemperaturaMedia())
            .descricao(p.getDescricao())
            .ehHabitavel(p.isEhHabitavel())
            .gravidade(p.getGravidade())
            .temSateliteNatural(p.isTemSateliteNatural())
            .luas((p.getLuas()))
            .quantidadeLuas(Optional.ofNullable(p.getLuas())
                    .map(List::size)
                    .orElse(0))
            .build())
            .orElse(null);
    }

    public static PlanetaResponseDTO of(Planeta planeta) {
        return from(planeta);
    }

    public static List<PlanetaResponseDTO> fromList(List<Planeta> planetas) {
        return Optional.ofNullable(planetas)
                .orElse(Collections.emptyList())
                .stream()
                .map(PlanetaResponseDTO::from)
                .toList();
    }

    private static List<LuaSimplificadaDTO> converterLuas(List<Lua> luas) {
        return Optional.ofNullable(luas)
                .orElse(Collections.emptyList())
                .stream()
                .map(LuaSimplificadaDTO::from)
                .toList();
    }

    public static class LuaSimplificadaDTO {
        
        private Integer id;
        private String nome;
        private Long distanciaOrbitral;
        private Boolean ehHabitavel;
        private Double temperaturaMedia;

        public static LuaSimplificadaDTO from(Lua lua) {
            if (lua == null) {
                return null;
            }
            LuaSimplificadaDTO dto = new LuaSimplificadaDTO();
            dto.id = lua.getId();
            dto.nome = lua.getNome();
            dto.distanciaOrbitral = lua.getDistanciaOrbitral();
            dto.ehHabitavel = lua.isEhHabitavel();
            dto.temperaturaMedia = lua.getTemperaturaMedia();
            return dto;
        }

        public static LuaSimplificadaDTO of(Lua lua) {
            return from(lua);
        }
    }
}
