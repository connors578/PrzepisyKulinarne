package pl.fmi.przepisykulinarne.kontroler;

public class ZapytaniePoOpisiePrzy implements ZapytanieWyszukiwania{

	@Override
	public String pobierzZapytanieKomunikatu(String warunek) {
		return "select nazwa from przepisy where opis like '%" + warunek + "%'";
	}

}
