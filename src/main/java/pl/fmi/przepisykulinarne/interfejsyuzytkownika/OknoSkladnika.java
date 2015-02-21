package pl.fmi.przepisykulinarne.interfejsyuzytkownika;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class OknoSkladnika {
	FlowPane layout;
	Button akceptuj;
	Button anuluj;
	TextField skladnik;

	public OknoSkladnika() {
		layout = new FlowPane();
		akceptuj = new Button("Akceptuj");
		anuluj = new Button("Anuluj");
		skladnik = new TextField();
		layout.getChildren().addAll(akceptuj, skladnik, anuluj);
	}
	
	public String pobierzSkladnik() {
		return skladnik.getText();
	}
}
