package br.com.renata.tabelaFipe.services;

import java.util.List;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe); //o conversor ta preparado pra obter uma entendida, uma representacao unica da classe
    <T> List<T> obterLista(String json, Class<T> classe);  //aqui eu quero uma lista
}
