package br.edu.infnet.vinicius_vianna;

import br.edu.infnet.vinicius_vianna.model.domain.Estrela;
import br.edu.infnet.vinicius_vianna.model.enums.ConstelacaoEnum;
import br.edu.infnet.vinicius_vianna.model.enums.LuminosidadeEnum;
import br.edu.infnet.vinicius_vianna.service.EstrelaService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

@Component
@Order(3)
public class EstrelaLoader implements ApplicationRunner {
    private final EstrelaService estrelaService;

    public EstrelaLoader(EstrelaService estrelaService) {
        this.estrelaService = estrelaService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileReader arquivo = new FileReader("estrela.txt");
        BufferedReader leitura = new BufferedReader(arquivo);
        String linha = leitura.readLine();
        String[] campos = null;

        while (linha != null) {
            campos = linha.split(";");

            Estrela estrela = new Estrela();
            estrela.setNome(campos[0]);
            estrela.setTemperaturaMedia(Double.valueOf(campos[1]));
            estrela.setDescricao(campos[2]);
            estrela.setEhHabitavel(Boolean.valueOf(campos[3]));
            estrela.setConstelacao(ConstelacaoEnum.valueOf(campos[4]));
            estrela.setLuminosidade(LuminosidadeEnum.valueOf(campos[5]));

            this.estrelaService.criar(estrela);
            System.out.println(estrela);
            linha = leitura.readLine();
        }
        List<Estrela> estrelas = this.estrelaService.obterTodos();
        estrelas.forEach(System.out::println);
        leitura.close();
    }

}
