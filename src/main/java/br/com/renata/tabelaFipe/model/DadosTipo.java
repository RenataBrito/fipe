package br.com.renata.tabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTipo(@JsonAlias("codigo") String codMarca,
                        @JsonAlias("nome") String marca) {

    @Override
    public String toString() {
        return "Cód: " + codMarca +
                " Descrição: " + marca;
    }
}
