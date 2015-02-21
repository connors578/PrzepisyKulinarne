package pl.fmi.przepisykulinarne.model;

public class Skladniki {
	private String nazwa;
	private Integer przepisId;
	
	public Skladniki() {
	}
	
	public Skladniki(String nazwa, Integer przepisId) {
		this.nazwa = nazwa;
		this.przepisId = przepisId;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public Integer getPrzepisId() {
		return przepisId;
	}

	public void setPrzepisId(Integer przepisId) {
		this.przepisId = przepisId;
	}
}
