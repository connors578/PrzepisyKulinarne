package pl.fmi.przepisykulinarne.model;

import java.util.ArrayList;

public class Przepis {
	String nazwa;
	String opisPrzygotowania;
	ArrayList<Skladniki> skladniki = null;
	
	public Przepis() {
		skladniki = new ArrayList<Skladniki>();
	}
	
	public ArrayList<Skladniki> getSkladniki() {
		return skladniki;
	}

	public void setSkladniki(ArrayList<Skladniki> skladniki) {
		this.skladniki = skladniki;
	}

	public Przepis(String nazwa, String opisPrzygotowania, ArrayList<Skladniki> skladniki) {
		this.nazwa = nazwa;
		this.opisPrzygotowania = opisPrzygotowania;
		this.skladniki = skladniki;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getOpisPrzygotowania() {
		return opisPrzygotowania;
	}

	public void setOpisPrzygotowania(String opisPrzygotowania) {
		this.opisPrzygotowania = opisPrzygotowania;
	}
}
