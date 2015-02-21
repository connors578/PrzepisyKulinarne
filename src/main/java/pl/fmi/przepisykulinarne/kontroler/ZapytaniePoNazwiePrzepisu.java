package pl.fmi.przepisykulinarne.kontroler;

public class ZapytaniePoNazwiePrzepisu implements ZapytanieWyszukiwania{

	@Override
	public String pobierzZapytanieKomunikatu(String warunek) {
		return "select nazwa from przepisy where nazwa like '%" + warunek + "%'";
	}

}
