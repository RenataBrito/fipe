package br.com.renata.tabelaFipe;

import br.com.renata.tabelaFipe.main.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TabelaFipeApplication {

	public static void main(String[] args) {
		Main main = new Main();
		main.exibeMenu();
	}
}
