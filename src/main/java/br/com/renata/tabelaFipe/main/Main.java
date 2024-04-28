package br.com.renata.tabelaFipe.main;

import br.com.renata.tabelaFipe.model.DadosModelo;
import br.com.renata.tabelaFipe.model.DadosTipo;
import br.com.renata.tabelaFipe.model.Modelo;
import br.com.renata.tabelaFipe.model.Veiculo;
import br.com.renata.tabelaFipe.services.ConsumoAPI;
import br.com.renata.tabelaFipe.services.ConverteDados;
import org.springframework.boot.Banner;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        System.out.println("**** OPÇÕES ****");
        System.out.println("carros");
        System.out.println("motos");
        System.out.println("caminhoes");
        System.out.println("");
        System.out.println("Digite uma das opções para consultar os valores: ");
        var tipo = leitura.nextLine();
        if (tipo.toLowerCase().contains("carr")){
            tipo = "carros";
        } else if (tipo.toLowerCase().contains("moto")){
            tipo = "motos";
        } else if (tipo.toLowerCase().contains("caminh")){
            tipo = "caminhoes";
        } else {
            System.out.println("Tipo invalido");
            return;
        }

        var enderecoCompleto = ENDERECO + tipo + "/marcas/";
        System.out.println(enderecoCompleto);
        var json = consumoAPI.obterDados(enderecoCompleto); //aqui vc ja pode printar pra ve oq veio no json
        //System.out.println(json); //aqui eu quero que isso se transforma em uma lista
        var tipos = conversor.obterLista(json, DadosTipo.class);
        tipos.stream()
                .sorted(Comparator.comparing(DadosTipo::marca)) //printa ordenado pelo codTipo
                .forEach(System.out::println);

        System.out.println("Informe o codigo da marca para consulta:");
        var codMarca = leitura.nextLine();
        enderecoCompleto = enderecoCompleto + codMarca + "/modelos/";
        json = consumoAPI.obterDados(enderecoCompleto);
        var modelosLista = conversor.obterDados(json, DadosModelo.class);//o dadosmodelo ja eh uma lista

        System.out.println("\nModelos dessa marca: ");
        modelosLista.modelos().stream()
                .sorted(Comparator.comparing(DadosTipo::codMarca)) //printa ordenado pelo codTipo
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do veiculo para consulta:");
        var trechoVeiculo = leitura.nextLine();

        List<DadosTipo> modelosFiltrados = modelosLista.modelos().stream()
                .filter(m -> m.marca().toLowerCase().contains(trechoVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nDigite por favor o codigo do modelo:");
        var codModeloFiltrado = leitura.nextLine();
        enderecoCompleto = enderecoCompleto + codModeloFiltrado + "/anos/";
        json = consumoAPI.obterDados(enderecoCompleto);
        List<DadosTipo> anos = conversor.obterLista(json, DadosTipo.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for(int i =0; i< anos.size(); i++){
            var enderecoAnos = enderecoCompleto  + anos.get(i).codMarca();
            json = consumoAPI.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        veiculos.forEach(System.out::println);

    }
}
