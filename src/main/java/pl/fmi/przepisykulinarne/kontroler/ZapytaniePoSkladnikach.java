package pl.fmi.przepisykulinarne.kontroler;

public class ZapytaniePoSkladnikach implements ZapytanieWyszukiwania{

	@Override
	public String pobierzZapytanieKomunikatu(String warunek) {
		return "select nazwa from przepisy where id = (select id_przepisu from skladniki where nazwa like '%" + warunek + "%')";
	}

}
