package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.service.ConsumoAPI;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.Comparator;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    public void exibeMenu() {
        var menu = """
                ***OPÇÕES***
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consulta: 
                """;

        System.out.println(menu);

        var opcao = leitura.nextLine();
        String endereco;

        if (opcao.toLowerCase().contains("carro")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("moto/marcas")) {
            endereco = URL_BASE + "moto/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);
        System.out.println(json);

        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca para consulta.");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessar marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
    }
}
