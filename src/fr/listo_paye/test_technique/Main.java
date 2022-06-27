package fr.listo_paye.test_technique;

import java.time.LocalDate;

public class Main {

	public static void main(String[] args) {

		LocalDate a = LocalDate.of(2022, 6, 2);
		LocalDate b = LocalDate.of(2023, 5, 5);
		String periodesConges;
		

		Periode dateConges = new Periode(a, b);
		periodesConges = dateConges.controle();
		
	}

}
