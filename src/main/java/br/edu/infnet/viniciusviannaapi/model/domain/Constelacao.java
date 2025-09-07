package br.edu.infnet.viniciusviannaapi.model.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Month;
import java.util.Set;

@Getter
@Setter
public class Constelacao {

    private Long id;
    private String nome;
    private String abreviacao;
    private Integer qntEstrelasPrincipais;
    private Boolean visivelAHumanos;
    private Set<Month> mesesVisiveis;

    public boolean ehVisivelEm(Month mes) {
        return mesesVisiveis != null && mesesVisiveis.contains(mes);
    }

    public boolean ehDoZodiaco() {
        return ehConstelacaoZodiacal(this.abreviacao);
    }

    private static boolean ehConstelacaoZodiacal(String abreviacao) {
       Set<String> zodiacoAbreviacoes = Set.of(
               "Ari", "Tau", "Gem", "Can", "Leo", "Vir",
               "Lib", "Sco", "Sag", "Cap", "Aqu", "Pis"
       );
       return zodiacoAbreviacoes.contains(abreviacao);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Estrelas Principais: %d - Visível a Humanos: %s - Meses Visíveis: %s",
                nome,
                abreviacao,
                qntEstrelasPrincipais,
                visivelAHumanos ? "Sim" : "Não",
                mesesVisiveis);
    }

}
