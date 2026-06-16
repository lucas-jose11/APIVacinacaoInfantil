package com.SENAI.apiVacinacaoInfantil;

import com.SENAI.apiVacinacaoInfantil.ClassesAuxiliares.Sistema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiVacinacaoInfantilApplication {

	public static void main(String[] args) {
		Sistema sistema = new Sistema();
		sistema.IniciarMenu();

		SpringApplication.run(ApiVacinacaoInfantilApplication.class, args);
	}


}
