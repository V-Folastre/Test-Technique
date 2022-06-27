package fr.listo_paye.test_technique;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PeriodeTest {

	@Test
	void test() {
		Periode test1 = new Periode(LocalDate.of(2022,5,12), LocalDate.of(2022,5,25));
		assertEquals("2022-05-12, null, null, null, 2022-05-25", test1.controle());
		
		Periode test2 = new Periode(LocalDate.of(2021,12,20), LocalDate.of(2022,1,3));
		assertEquals("2021-12-20, 2021-12-31, null, 2022-01-01, 2022-01-03", test2.controle());
		
		Periode test3 = new Periode(LocalDate.of(2022,4,10), LocalDate.of(2022,9,25));
		assertEquals("2022-04-10, 2022-04-30, [2022-05-01, 2022-05-31, 2022-06-01, "
				+ "2022-06-30, 2022-07-01, 2022-07-31, 2022-08-01, 2022-08-31], "
				+ "2022-09-01, 2022-09-25", test3.controle());
	}

}
