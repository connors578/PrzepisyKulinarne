package pl.fmi.przepisykulinarne.kontroler;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZapytanieWyszukiwaniaTest {
	String warunek = "warunek";
	
	@SuppressWarnings("depracated")
	@Test
	public void zapytaniePoNazwiePrzepisu() {
		assertEquals("select nazwa from przepisy where nazwa like '%" + warunek + "%'", new ZapytaniePoNazwiePrzepisu().pobierzZapytanieKomunikatu(warunek));
	}
	
	@SuppressWarnings("depracated")
	@Test
	public void zapytaniePoOpisiePrzy() {
		assertEquals("select nazwa from przepisy where opis like '%" + warunek + "%'", new ZapytaniePoOpisiePrzy().pobierzZapytanieKomunikatu(warunek));
	}
	
	@SuppressWarnings("depracated")
	@Test
	public void zapytaniePoSkladnikach() {
		assertEquals("select nazwa from przepisy where id = (select id_przepisu from skladniki where nazwa like '%" + warunek + "%')", new ZapytaniePoSkladnikach().pobierzZapytanieKomunikatu(warunek));
	}

}
